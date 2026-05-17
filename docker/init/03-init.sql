-- =========================================================
-- EXTENSÕES
-- =========================================================

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =========================================================
-- TABELAS DE USUÁRIO
-- De acordo com o diagrama de classe
-- =========================================================

DROP VIEW IF EXISTS vw_user_full_profile;
DROP VIEW IF EXISTS vw_preference_with_user;
DROP VIEW IF EXISTS vw_device_with_user;

DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS alert CASCADE;
DROP TABLE IF EXISTS favorite CASCADE;
DROP TABLE IF EXISTS profiles CASCADE;
DROP TABLE IF EXISTS preference CASCADE;
DROP TABLE IF EXISTS device CASCADE;

CREATE TABLE device (
    device_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_token  UUID UNIQUE NOT NULL,
    platform      platform_enum NOT NULL,
    app_version   VARCHAR(50),
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
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
-- TABELAS NOVAS DO DIAGRAMA
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
        ON DELETE CASCADE
);

CREATE TABLE alert (
    alert_id        BIGSERIAL PRIMARY KEY,
    minutes_before  VARCHAR(50),
    active          BOOLEAN NOT NULL DEFAULT TRUE,
    profile_id      UUID NOT NULL,
    route_id        BIGINT NOT NULL,
    stop_id         BIGINT NOT NULL,

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
    scheduled_time   TIMESTAMP,
    sent_at          TIMESTAMP,
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
-- O diagrama mostra shapePointId como FK em Shape.
-- Como isso pode gerar circularidade, deixei como campo opcional.
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

-- =========================================================
-- FUNCTION PARA updated_at
-- =========================================================

CREATE OR REPLACE FUNCTION fn_set_updated_at()
RETURNS TRIGGER
AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
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
        updated_at = CURRENT_TIMESTAMP;
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