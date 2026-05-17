-- =========================================================
-- EXTENSÕES
-- =========================================================

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================================================
-- LIMPEZA DAS VIEWS
-- =========================================================

DROP VIEW IF EXISTS vw_notification_details;
DROP VIEW IF EXISTS vw_alert_details;
DROP VIEW IF EXISTS vw_favorite_details;
DROP VIEW IF EXISTS vw_profile_full;
DROP VIEW IF EXISTS vw_user_full_profile;
DROP VIEW IF EXISTS vw_preference_with_user;
DROP VIEW IF EXISTS vw_device_with_user;

-- =========================================================
-- LIMPEZA DAS TABELAS NOVAS / USUÁRIO
-- =========================================================

DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS alert CASCADE;
DROP TABLE IF EXISTS favorite CASCADE;
DROP TABLE IF EXISTS profiles CASCADE;
DROP TABLE IF EXISTS preference CASCADE;
DROP TABLE IF EXISTS device CASCADE;

-- =========================================================
-- TABELAS DE USUÁRIO
-- =========================================================

CREATE TABLE device (
    device_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_token   UUID UNIQUE NOT NULL,
    platform       platform_enum NOT NULL,
    app_version    VARCHAR(50),
    active         BOOLEAN NOT NULL DEFAULT TRUE,
    token_version  INTEGER NOT NULL DEFAULT 1,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE preference (
    preference_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transport_type    transport_type_enum,
    route_preference  route_preference_enum,
    slow_pace         BOOLEAN NOT NULL DEFAULT FALSE,
    max_walking_time  INTEGER,
    updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_preference_max_walking_time
        CHECK (max_walking_time IS NULL OR max_walking_time >= 0)
);

CREATE TABLE profiles (
    profile_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email          VARCHAR(255) UNIQUE,
    device_id      UUID UNIQUE,
    preference_id  UUID UNIQUE,

    CONSTRAINT fk_profile_device
        FOREIGN KEY (device_id) REFERENCES device(device_id)
        ON DELETE SET NULL,

    CONSTRAINT fk_profile_preference
        FOREIGN KEY (preference_id) REFERENCES preference(preference_id)
        ON DELETE SET NULL
);

-- =========================================================
-- AJUSTES POSTGIS NAS TABELAS GTFS
-- =========================================================

ALTER TABLE stop
ADD COLUMN IF NOT EXISTS location GEOMETRY(Point, 4326);

UPDATE stop
SET location = ST_SetSRID(ST_MakePoint(stop_longitude, stop_latitude), 4326)
WHERE location IS NULL
  AND stop_longitude IS NOT NULL
  AND stop_latitude IS NOT NULL;

ALTER TABLE shape_point
ADD COLUMN IF NOT EXISTS location GEOMETRY(Point, 4326);

UPDATE shape_point
SET location = ST_SetSRID(ST_MakePoint(shape_point_longitude, shape_point_latitude), 4326)
WHERE location IS NULL
  AND shape_point_longitude IS NOT NULL
  AND shape_point_latitude IS NOT NULL;

-- =========================================================
-- TABELAS NOVAS DO DIAGRAMA
-- route_id, stop_id e trip_id mantidos como BIGINT.
-- =========================================================

CREATE TABLE favorite (
    favorite_id    BIGSERIAL PRIMARY KEY,
    favorite_name  VARCHAR(255),
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    route_id       BIGINT NOT NULL,
    profile_id     UUID NOT NULL,

    CONSTRAINT fk_favorite_route
        FOREIGN KEY (route_id) REFERENCES route(route_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_favorite_profile
        FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
        ON DELETE CASCADE,

    CONSTRAINT uq_favorite_profile_route
        UNIQUE (profile_id, route_id)
);

CREATE TABLE alert (
    alert_id        BIGSERIAL PRIMARY KEY,
    minutes_before  INTEGER NOT NULL DEFAULT 10,
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    profile_id      UUID NOT NULL,
    route_id        BIGINT NOT NULL,
    stop_id         BIGINT NOT NULL,

    CONSTRAINT chk_alert_minutes_before
        CHECK (minutes_before >= 0),

    CONSTRAINT fk_alert_profile
        FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_alert_route
        FOREIGN KEY (route_id) REFERENCES route(route_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_alert_stop
        FOREIGN KEY (stop_id) REFERENCES stop(stop_id)
        ON DELETE CASCADE
);

CREATE TABLE notification (
    notification_id  BIGSERIAL PRIMARY KEY,
    scheduled_time   TIMESTAMP NOT NULL,
    sent_at          TIMESTAMP,
    status           notification_status_enum NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    trip_id          BIGINT NOT NULL,
    alert_id         BIGINT NOT NULL,
    profile_id       UUID NOT NULL,

    CONSTRAINT fk_notification_trip
        FOREIGN KEY (trip_id) REFERENCES trip(trip_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_notification_alert
        FOREIGN KEY (alert_id) REFERENCES alert(alert_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_notification_profile
        FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
        ON DELETE CASCADE
);

-- =========================================================
-- AJUSTE OPCIONAL EM SHAPE
-- =========================================================

ALTER TABLE shape
ADD COLUMN IF NOT EXISTS shape_point_id VARCHAR(100);

ALTER TABLE shape
DROP CONSTRAINT IF EXISTS fk_shape_shape_point;

ALTER TABLE shape
ADD CONSTRAINT fk_shape_shape_point
    FOREIGN KEY (shape_point_id) REFERENCES shape_point(shape_point_id)
    ON DELETE SET NULL;

-- =========================================================
-- ÍNDICES
-- =========================================================

CREATE INDEX IF NOT EXISTS idx_device_token
    ON device(device_token);

CREATE INDEX IF NOT EXISTS idx_profiles_email
    ON profiles(email);

CREATE INDEX IF NOT EXISTS idx_profiles_device_id
    ON profiles(device_id);

CREATE INDEX IF NOT EXISTS idx_profiles_preference_id
    ON profiles(preference_id);

CREATE INDEX IF NOT EXISTS idx_favorite_route_id
    ON favorite(route_id);

CREATE INDEX IF NOT EXISTS idx_favorite_profile_id
    ON favorite(profile_id);

CREATE INDEX IF NOT EXISTS idx_alert_profile_id
    ON alert(profile_id);

CREATE INDEX IF NOT EXISTS idx_alert_route_id
    ON alert(route_id);

CREATE INDEX IF NOT EXISTS idx_alert_stop_id
    ON alert(stop_id);

CREATE INDEX IF NOT EXISTS idx_notification_trip_id
    ON notification(trip_id);

CREATE INDEX IF NOT EXISTS idx_notification_alert_id
    ON notification(alert_id);

CREATE INDEX IF NOT EXISTS idx_notification_profile_id
    ON notification(profile_id);

CREATE INDEX IF NOT EXISTS idx_stop_location
    ON stop USING GIST(location);

CREATE INDEX IF NOT EXISTS idx_shape_point_location
    ON shape_point USING GIST(location);

CREATE INDEX IF NOT EXISTS idx_shape_shape_point_id
    ON shape(shape_point_id);

-- =========================================================
-- VIEWS
-- =========================================================

CREATE OR REPLACE VIEW vw_device_with_user AS
SELECT
    d.device_id,
    d.device_token,
    d.platform,
    d.app_version,
    d.active,
    d.token_version,
    d.created_at AS device_created_at,
    d.updated_at AS device_updated_at,
    p.profile_id,
    p.email
FROM device d
LEFT JOIN profiles p
    ON p.device_id = d.device_id;

CREATE OR REPLACE VIEW vw_preference_with_user AS
SELECT
    pr.preference_id,
    pr.transport_type,
    pr.route_preference,
    pr.slow_pace,
    pr.max_walking_time,
    pr.updated_at AS preference_updated_at,
    p.profile_id,
    p.email
FROM preference pr
LEFT JOIN profiles p
    ON p.preference_id = pr.preference_id;

CREATE OR REPLACE VIEW vw_user_full_profile AS
SELECT
    p.profile_id,
    p.email,

    d.device_id,
    d.device_token,
    d.platform,
    d.app_version,
    d.active,
    d.token_version,
    d.created_at AS device_created_at,
    d.updated_at AS device_updated_at,

    pr.preference_id,
    pr.transport_type,
    pr.route_preference,
    pr.slow_pace,
    pr.max_walking_time,
    pr.updated_at AS preference_updated_at
FROM profiles p
LEFT JOIN device d
    ON p.device_id = d.device_id
LEFT JOIN preference pr
    ON p.preference_id = pr.preference_id;

CREATE OR REPLACE VIEW vw_profile_full AS
SELECT *
FROM vw_user_full_profile;

CREATE OR REPLACE VIEW vw_favorite_details AS
SELECT
    f.favorite_id,
    f.favorite_name,
    f.created_at,
    p.profile_id,
    p.email,
    r.route_id,
    r.route_short_name,
    r.route_long_name
FROM favorite f
JOIN profiles p
    ON p.profile_id = f.profile_id
JOIN route r
    ON r.route_id = f.route_id;

CREATE OR REPLACE VIEW vw_alert_details AS
SELECT
    a.alert_id,
    a.minutes_before,
    a.active,
    a.created_at,
    p.profile_id,
    p.email,
    r.route_id,
    r.route_short_name,
    r.route_long_name,
    s.stop_id,
    s.stop_name,
    s.stop_description,
    s.stop_latitude,
    s.stop_longitude
FROM alert a
JOIN profiles p
    ON p.profile_id = a.profile_id
JOIN route r
    ON r.route_id = a.route_id
JOIN stop s
    ON s.stop_id = a.stop_id;

CREATE OR REPLACE VIEW vw_notification_details AS
SELECT
    n.notification_id,
    n.scheduled_time,
    n.sent_at,
    n.status,
    n.created_at,
    p.profile_id,
    p.email,
    a.alert_id,
    a.minutes_before,
    r.route_id,
    r.route_short_name,
    s.stop_id,
    s.stop_name,
    t.trip_id,
    t.trip_headsign
FROM notification n
JOIN profiles p
    ON p.profile_id = n.profile_id
JOIN alert a
    ON a.alert_id = n.alert_id
JOIN route r
    ON r.route_id = a.route_id
JOIN stop s
    ON s.stop_id = a.stop_id
JOIN trip t
    ON t.trip_id = n.trip_id;

-- =========================================================
-- FUNCTIONS
-- =========================================================

CREATE OR REPLACE FUNCTION fn_set_updated_at()
RETURNS TRIGGER
AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fn_make_point(
    p_longitude DOUBLE PRECISION,
    p_latitude DOUBLE PRECISION
)
RETURNS GEOMETRY(Point, 4326)
AS $$
BEGIN
    IF p_longitude IS NULL OR p_latitude IS NULL THEN
        RETURN NULL;
    END IF;

    RETURN ST_SetSRID(ST_MakePoint(p_longitude, p_latitude), 4326);
END;
$$ LANGUAGE plpgsql IMMUTABLE;

CREATE OR REPLACE FUNCTION fn_distance_between_points_meters(
    p_origin_longitude DOUBLE PRECISION,
    p_origin_latitude DOUBLE PRECISION,
    p_destination_longitude DOUBLE PRECISION,
    p_destination_latitude DOUBLE PRECISION
)
RETURNS DOUBLE PRECISION
AS $$
BEGIN
    RETURN ST_Distance(
        fn_make_point(p_origin_longitude, p_origin_latitude)::geography,
        fn_make_point(p_destination_longitude, p_destination_latitude)::geography
    );
END;
$$ LANGUAGE plpgsql IMMUTABLE;

CREATE OR REPLACE FUNCTION fn_distance_to_stop_meters(
    p_stop_id BIGINT,
    p_user_longitude DOUBLE PRECISION,
    p_user_latitude DOUBLE PRECISION
)
RETURNS DOUBLE PRECISION
AS $$
DECLARE
    v_distance DOUBLE PRECISION;
BEGIN
    SELECT ST_Distance(
        s.location::geography,
        fn_make_point(p_user_longitude, p_user_latitude)::geography
    )
    INTO v_distance
    FROM stop s
    WHERE s.stop_id = p_stop_id;

    RETURN v_distance;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fn_get_nearby_stops(
    p_user_longitude DOUBLE PRECISION,
    p_user_latitude DOUBLE PRECISION,
    p_radius_meters DOUBLE PRECISION
)
RETURNS TABLE (
    stop_id BIGINT,
    stop_name VARCHAR,
    stop_description TEXT,
    stop_latitude DOUBLE PRECISION,
    stop_longitude DOUBLE PRECISION,
    distance_meters DOUBLE PRECISION
)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        s.stop_id,
        s.stop_name,
        s.stop_description,
        s.stop_latitude,
        s.stop_longitude,
        ST_Distance(
            s.location::geography,
            fn_make_point(p_user_longitude, p_user_latitude)::geography
        ) AS distance_meters
    FROM stop s
    WHERE s.location IS NOT NULL
      AND ST_DWithin(
            s.location::geography,
            fn_make_point(p_user_longitude, p_user_latitude)::geography,
            p_radius_meters
      )
    ORDER BY distance_meters;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fn_get_profile_id_by_email(
    p_email VARCHAR
)
RETURNS UUID
AS $$
DECLARE
    v_profile_id UUID;
BEGIN
    SELECT profile_id
    INTO v_profile_id
    FROM profiles
    WHERE email = p_email;

    RETURN v_profile_id;
END;
$$ LANGUAGE plpgsql;

-- =========================================================
-- TRIGGERS
-- =========================================================

DROP TRIGGER IF EXISTS trg_device_set_updated_at ON device;
DROP TRIGGER IF EXISTS trg_preference_set_updated_at ON preference;

CREATE TRIGGER trg_device_set_updated_at
BEFORE UPDATE ON device
FOR EACH ROW
EXECUTE FUNCTION fn_set_updated_at();

CREATE TRIGGER trg_preference_set_updated_at
BEFORE UPDATE ON preference
FOR EACH ROW
EXECUTE FUNCTION fn_set_updated_at();

-- =========================================================
-- STORED PROCEDURES
-- =========================================================

DROP PROCEDURE IF EXISTS sp_create_device(
    UUID,
    UUID,
    platform_enum,
    VARCHAR
);

CREATE OR REPLACE PROCEDURE sp_create_device(
    p_device_id UUID,
    p_device_token UUID,
    p_platform platform_enum,
    p_app_version VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO device (
        device_id,
        device_token,
        platform,
        app_version
    )
    VALUES (
        COALESCE(p_device_id, gen_random_uuid()),
        p_device_token,
        p_platform,
        p_app_version
    )
    ON CONFLICT (device_token)
    DO UPDATE SET
        platform = EXCLUDED.platform,
        app_version = EXCLUDED.app_version,
        active = TRUE,
        token_version = device.token_version + 1,
        updated_at = CURRENT_TIMESTAMP;
END;
$$;

DROP PROCEDURE IF EXISTS sp_deactivate_device(UUID);

CREATE OR REPLACE PROCEDURE sp_deactivate_device(
    p_device_id UUID
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE device
    SET active = FALSE,
        updated_at = CURRENT_TIMESTAMP
    WHERE device_id = p_device_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_upsert_preference(
    UUID,
    transport_type_enum,
    route_preference_enum,
    BOOLEAN,
    INTEGER
);

CREATE OR REPLACE PROCEDURE sp_upsert_preference(
    p_preference_id UUID,
    p_transport_type transport_type_enum,
    p_route_preference route_preference_enum,
    p_slow_pace BOOLEAN,
    p_max_walking_time INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO preference (
        preference_id,
        transport_type,
        route_preference,
        slow_pace,
        max_walking_time
    )
    VALUES (
        COALESCE(p_preference_id, gen_random_uuid()),
        p_transport_type,
        p_route_preference,
        COALESCE(p_slow_pace, FALSE),
        p_max_walking_time
    )
    ON CONFLICT (preference_id)
    DO UPDATE SET
        transport_type = EXCLUDED.transport_type,
        route_preference = EXCLUDED.route_preference,
        slow_pace = EXCLUDED.slow_pace,
        max_walking_time = EXCLUDED.max_walking_time,
        updated_at = CURRENT_TIMESTAMP;
END;
$$;

DROP PROCEDURE IF EXISTS sp_create_or_link_profile(
    UUID,
    VARCHAR,
    UUID,
    UUID
);

CREATE OR REPLACE PROCEDURE sp_create_or_link_profile(
    p_profile_id UUID,
    p_email VARCHAR,
    p_device_id UUID,
    p_preference_id UUID
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO profiles (
        profile_id,
        email,
        device_id,
        preference_id
    )
    VALUES (
        COALESCE(p_profile_id, gen_random_uuid()),
        p_email,
        p_device_id,
        p_preference_id
    )
    ON CONFLICT (profile_id)
    DO UPDATE SET
        email = EXCLUDED.email,
        device_id = EXCLUDED.device_id,
        preference_id = EXCLUDED.preference_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_add_favorite(
    UUID,
    BIGINT,
    VARCHAR
);

CREATE OR REPLACE PROCEDURE sp_add_favorite(
    p_profile_id UUID,
    p_route_id BIGINT,
    p_favorite_name VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO favorite (
        profile_id,
        route_id,
        favorite_name
    )
    VALUES (
        p_profile_id,
        p_route_id,
        p_favorite_name
    )
    ON CONFLICT (profile_id, route_id)
    DO UPDATE SET
        favorite_name = EXCLUDED.favorite_name;
END;
$$;

DROP PROCEDURE IF EXISTS sp_remove_favorite(
    UUID,
    BIGINT
);

CREATE OR REPLACE PROCEDURE sp_remove_favorite(
    p_profile_id UUID,
    p_route_id BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM favorite
    WHERE profile_id = p_profile_id
      AND route_id = p_route_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_create_alert(
    UUID,
    BIGINT,
    BIGINT,
    INTEGER
);

CREATE OR REPLACE PROCEDURE sp_create_alert(
    p_profile_id UUID,
    p_route_id BIGINT,
    p_stop_id BIGINT,
    p_minutes_before INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO alert (
        profile_id,
        route_id,
        stop_id,
        minutes_before
    )
    VALUES (
        p_profile_id,
        p_route_id,
        p_stop_id,
        COALESCE(p_minutes_before, 10)
    );
END;
$$;

DROP PROCEDURE IF EXISTS sp_set_alert_active(
    BIGINT,
    BOOLEAN
);

CREATE OR REPLACE PROCEDURE sp_set_alert_active(
    p_alert_id BIGINT,
    p_active BOOLEAN
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE alert
    SET active = COALESCE(p_active, active)
    WHERE alert_id = p_alert_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_schedule_notification(
    BIGINT,
    UUID,
    BIGINT,
    TIMESTAMP
);

CREATE OR REPLACE PROCEDURE sp_schedule_notification(
    p_alert_id BIGINT,
    p_profile_id UUID,
    p_trip_id BIGINT,
    p_scheduled_time TIMESTAMP
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO notification (
        alert_id,
        profile_id,
        trip_id,
        scheduled_time,
        status
    )
    VALUES (
        p_alert_id,
        p_profile_id,
        p_trip_id,
        p_scheduled_time,
        'PENDING'
    );
END;
$$;

DROP PROCEDURE IF EXISTS sp_mark_notification_sent(
    BIGINT
);

CREATE OR REPLACE PROCEDURE sp_mark_notification_sent(
    p_notification_id BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE notification
    SET status = 'SENT',
        sent_at = CURRENT_TIMESTAMP
    WHERE notification_id = p_notification_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_mark_notification_failed(
    BIGINT
);

CREATE OR REPLACE PROCEDURE sp_mark_notification_failed(
    p_notification_id BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE notification
    SET status = 'FAILED'
    WHERE notification_id = p_notification_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_cancel_notification(
    BIGINT
);

CREATE OR REPLACE PROCEDURE sp_cancel_notification(
    p_notification_id BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE notification
    SET status = 'CANCELLED'
    WHERE notification_id = p_notification_id;
END;
$$;

DROP PROCEDURE IF EXISTS sp_refresh_stop_locations();

CREATE OR REPLACE PROCEDURE sp_refresh_stop_locations()
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE stop
    SET location = ST_SetSRID(ST_MakePoint(stop_longitude, stop_latitude), 4326)
    WHERE stop_longitude IS NOT NULL
      AND stop_latitude IS NOT NULL;
END;
$$;

DROP PROCEDURE IF EXISTS sp_refresh_shape_point_locations();

CREATE OR REPLACE PROCEDURE sp_refresh_shape_point_locations()
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE shape_point
    SET location = ST_SetSRID(ST_MakePoint(shape_point_longitude, shape_point_latitude), 4326)
    WHERE shape_point_longitude IS NOT NULL
      AND shape_point_latitude IS NOT NULL;
END;
$$;
