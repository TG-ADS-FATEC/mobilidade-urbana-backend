-- =========================================================
-- EXTENSÃO
-- =========================================================

CREATE EXTENSION IF NOT EXISTS postgis;

-- =========================================================
-- ENUMS
-- =========================================================

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'platform_enum') THEN
        CREATE TYPE platform_enum AS ENUM ('ANDROID', 'IOS');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'route_preference_enum') THEN
        CREATE TYPE route_preference_enum AS ENUM ('FASTEST', 'LESS_WALKING', 'FEWER_TRANSFERS');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'transport_type_enum') THEN
        CREATE TYPE transport_type_enum AS ENUM ('BUS', 'TRAIN', 'SUBWAY');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'direction_id_enum') THEN
        CREATE TYPE direction_id_enum AS ENUM ('INITIAL_POINT', 'END_POINT');
    END IF;
END $$;

-- =========================================================
-- TABELAS PRINCIPAIS
-- =========================================================

CREATE TABLE IF NOT EXISTS agency (
    agency_id        BIGINT PRIMARY KEY,
    agency_name      VARCHAR(255) NOT NULL,
    agency_url       VARCHAR(500),
    agency_time_zone VARCHAR(100),
    agency_language  VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS route (
    route_id          BIGINT PRIMARY KEY,
    agency_id         BIGINT NOT NULL,
    route_short_name  VARCHAR(100),
    route_long_name   VARCHAR(255),
    route_type        INTEGER,
    route_color       VARCHAR(20),
    route_text_color  VARCHAR(20),

    CONSTRAINT fk_route_agency
        FOREIGN KEY (agency_id) REFERENCES agency (agency_id)
);

CREATE TABLE IF NOT EXISTS shape (
    shape_id        VARCHAR(100) PRIMARY KEY,
    geometry        geometry(LineString, 4326),
    total_distance  DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS calendar (
    service_id   VARCHAR(100) PRIMARY KEY,
    monday       BOOLEAN DEFAULT FALSE,
    tuesday      BOOLEAN DEFAULT FALSE,
    wednesday    BOOLEAN DEFAULT FALSE,
    thursday     BOOLEAN DEFAULT FALSE,
    friday       BOOLEAN DEFAULT FALSE,
    saturday     BOOLEAN DEFAULT FALSE,
    sunday       BOOLEAN DEFAULT FALSE,
    start_date   TIMESTAMP,
    end_date     TIMESTAMP
);

CREATE TABLE IF NOT EXISTS trip (
    trip_id          BIGINT PRIMARY KEY,
    route_id         BIGINT NOT NULL,
    service_id       VARCHAR(100) NOT NULL,
    shape_id         VARCHAR(100),
    trip_headsign    VARCHAR(255),
    direction_id     direction_id_enum,

    CONSTRAINT fk_trip_route
        FOREIGN KEY (route_id) REFERENCES route (route_id),

    CONSTRAINT fk_trip_calendar
        FOREIGN KEY (service_id) REFERENCES calendar (service_id),

    CONSTRAINT fk_trip_shape
        FOREIGN KEY (shape_id) REFERENCES shape (shape_id)
);

CREATE TABLE IF NOT EXISTS frequency (
    frequency_id    BIGSERIAL PRIMARY KEY,
    trip_id         BIGINT NOT NULL,
    start_time      TIME NOT NULL,
    end_time        TIME NOT NULL,
    headway_seconds INTEGER NOT NULL,

    CONSTRAINT fk_frequency_trip
        FOREIGN KEY (trip_id) REFERENCES trip (trip_id)
);

CREATE TABLE IF NOT EXISTS shape_point (
    shape_point_id         VARCHAR(100) PRIMARY KEY,
    shape_id               VARCHAR(100) NOT NULL,
    shape_point_latitude   DOUBLE PRECISION,
    shape_point_longitude  DOUBLE PRECISION,
    shape_point_sequence   INTEGER NOT NULL,
    distance_traveled      DOUBLE PRECISION,

    CONSTRAINT fk_shape_point_shape
        FOREIGN KEY (shape_id) REFERENCES shape (shape_id)
);

CREATE TABLE IF NOT EXISTS stop (
    stop_id            BIGINT PRIMARY KEY,
    stop_name          VARCHAR(255) NOT NULL,
    stop_description   TEXT,
    stop_latitude      DOUBLE PRECISION,
    stop_longitude     DOUBLE PRECISION,
    location           geometry(Point, 4326)
);

CREATE TABLE IF NOT EXISTS stop_time (
    trip_id          BIGINT NOT NULL,
    stop_id          BIGINT NOT NULL,
    arrival_time     TIME,
    departure_time   TIME,
    stop_sequence    INTEGER NOT NULL,

    PRIMARY KEY (trip_id, stop_sequence),

    CONSTRAINT fk_stop_time_trip
        FOREIGN KEY (trip_id) REFERENCES trip (trip_id),

    CONSTRAINT fk_stop_time_stop
        FOREIGN KEY (stop_id) REFERENCES stop (stop_id)
);

-- =========================================================
-- TABELAS DE USUÁRIO
-- =========================================================

CREATE TABLE IF NOT EXISTS device (
    device_token   UUID PRIMARY KEY,
    platform       platform_enum NOT NULL,
    app_version    VARCHAR(50),
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS preference (
    preference_id       BIGSERIAL PRIMARY KEY,
    transport_type      transport_type_enum,
    route_preference    route_preference_enum,
    slow_pace           BOOLEAN NOT NULL DEFAULT FALSE,
    max_walking_time    INTEGER,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_preference_max_walking_time
        CHECK (max_walking_time IS NULL OR max_walking_time >= 0)
);

CREATE TABLE IF NOT EXISTS profiles (
    profile_id         BIGSERIAL PRIMARY KEY,
    email              VARCHAR(255) UNIQUE,
    device_token       UUID UNIQUE,
    preference_id      BIGINT,
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_device
        FOREIGN KEY (device_token) REFERENCES device(device_token)
        ON DELETE SET NULL,

    CONSTRAINT fk_user_preference
        FOREIGN KEY (preference_id) REFERENCES preference(preference_id)
        ON DELETE SET NULL
);

-- =========================================================
-- ÍNDICES
-- =========================================================

CREATE INDEX IF NOT EXISTS idx_route_agency_id
    ON route (agency_id);

CREATE INDEX IF NOT EXISTS idx_trip_route_id
    ON trip (route_id);

CREATE INDEX IF NOT EXISTS idx_trip_service_id
    ON trip (service_id);

CREATE INDEX IF NOT EXISTS idx_trip_shape_id
    ON trip (shape_id);

CREATE INDEX IF NOT EXISTS idx_frequency_trip_id
    ON frequency (trip_id);

CREATE INDEX IF NOT EXISTS idx_shape_point_shape_id
    ON shape_point (shape_id);

CREATE INDEX IF NOT EXISTS idx_stop_time_trip_id
    ON stop_time (trip_id);

CREATE INDEX IF NOT EXISTS idx_stop_time_stop_id
    ON stop_time (stop_id);

CREATE INDEX IF NOT EXISTS idx_stop_location
    ON stop USING GIST (location);

CREATE INDEX IF NOT EXISTS idx_shape_geometry
    ON shape USING GIST (geometry);

CREATE INDEX IF NOT EXISTS idx_users_email
    ON users(email);

CREATE INDEX IF NOT EXISTS idx_users_device_token
    ON users(device_token);

CREATE INDEX IF NOT EXISTS idx_preference_device_token
    ON preference(device_token);

-- =========================================================
-- VIEWS
-- =========================================================

DROP VIEW IF EXISTS vw_device_with_user;
DROP VIEW IF EXISTS vw_preference_with_user;
DROP VIEW IF EXISTS vw_user_full_profile;

CREATE OR REPLACE VIEW vw_device_with_user AS
SELECT
    d.device_token,
    d.platform,
    d.app_version,
    d.created_at AS device_created_at,
    d.updated_at AS device_updated_at,
    u.user_id,
    u.email,
    u.created_at AS user_created_at,
    u.updated_at AS user_updated_at
FROM device d
LEFT JOIN users u
    ON u.device_token = d.device_token;

CREATE OR REPLACE VIEW vw_preference_with_user AS
SELECT
    p.preference_id,
    p.device_token,
    p.transport_type,
    p.route_preference,
    p.slow_pace,
    p.max_walking_time,
    p.created_at AS preference_created_at,
    p.updated_at AS preference_updated_at,
    u.user_id,
    u.email,
    u.created_at AS user_created_at,
    u.updated_at AS user_updated_at
FROM preference p
LEFT JOIN users u
    ON u.device_token = p.device_token;

CREATE OR REPLACE VIEW vw_user_full_profile AS
SELECT
    d.device_token,
    d.platform,
    d.app_version,
    d.created_at AS device_created_at,
    d.updated_at AS device_updated_at,
    p.preference_id,
    p.transport_type,
    p.route_preference,
    p.slow_pace,
    p.max_walking_time,
    p.created_at AS preference_created_at,
    p.updated_at AS preference_updated_at,
    u.user_id,
    u.email,
    u.created_at AS user_created_at,
    u.updated_at AS user_updated_at
FROM device d
LEFT JOIN preference p
    ON p.device_token = d.device_token
LEFT JOIN users u
    ON u.device_token = d.device_token;

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

-- =========================================================
-- TRIGGERS
-- =========================================================

DROP TRIGGER IF EXISTS trg_users_set_updated_at ON users;
DROP TRIGGER IF EXISTS trg_device_set_updated_at ON device;
DROP TRIGGER IF EXISTS trg_preference_set_updated_at ON preference;

CREATE TRIGGER trg_users_set_updated_at
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION fn_set_updated_at();

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

DROP PROCEDURE IF EXISTS sp_create_device_preference(
    UUID,
    platform_enum,
    VARCHAR,
    transport_type_enum,
    route_preference_enum,
    BOOLEAN,
    INTEGER
);

CREATE OR REPLACE PROCEDURE sp_create_device_preference(
    p_device_token UUID,
    p_platform platform_enum,
    p_app_version VARCHAR,
    p_transport_type transport_type_enum,
    p_route_preference route_preference_enum,
    p_slow_pace BOOLEAN,
    p_max_walking_time INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO device (
        device_token,
        platform,
        app_version
    )
    VALUES (
        p_device_token,
        p_platform,
        p_app_version
    )
    ON CONFLICT (device_token)
    DO UPDATE SET
        platform = EXCLUDED.platform,
        app_version = EXCLUDED.app_version,
        updated_at = CURRENT_TIMESTAMP;

    INSERT INTO preference (
        device_token,
        transport_type,
        route_preference,
        slow_pace,
        max_walking_time
    )
    VALUES (
        p_device_token,
        p_transport_type,
        p_route_preference,
        COALESCE(p_slow_pace, FALSE),
        p_max_walking_time
    )
    ON CONFLICT (device_token)
    DO UPDATE SET
        transport_type = EXCLUDED.transport_type,
        route_preference = EXCLUDED.route_preference,
        slow_pace = EXCLUDED.slow_pace,
        max_walking_time = EXCLUDED.max_walking_time,
        updated_at = CURRENT_TIMESTAMP;
END;
$$;

DROP PROCEDURE IF EXISTS sp_create_or_link_user(
    BIGINT,
    VARCHAR,
    UUID
);

CREATE OR REPLACE PROCEDURE sp_create_or_link_user(
    p_user_id BIGINT,
    p_email VARCHAR,
    p_device_token UUID
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO users (
        user_id,
        email,
        device_token
    )
    VALUES (
        p_user_id,
        p_email,
        p_device_token
    )
    ON CONFLICT (user_id)
    DO UPDATE SET
        email = EXCLUDED.email,
        device_token = EXCLUDED.device_token,
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
    p_device_token UUID,
    p_transport_type transport_type_enum,
    p_route_preference route_preference_enum,
    p_slow_pace BOOLEAN,
    p_max_walking_time INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO preference (
        device_token,
        transport_type,
        route_preference,
        slow_pace,
        max_walking_time
    )
    VALUES (
        p_device_token,
        p_transport_type,
        p_route_preference,
        COALESCE(p_slow_pace, FALSE),
        p_max_walking_time
    )
    ON CONFLICT (device_token)
    DO UPDATE SET
        transport_type = EXCLUDED.transport_type,
        route_preference = EXCLUDED.route_preference,
        slow_pace = EXCLUDED.slow_pace,
        max_walking_time = EXCLUDED.max_walking_time,
        updated_at = CURRENT_TIMESTAMP;
END;
$$;

-- =========================================================
-- CRUD - USERS
-- =========================================================

-- INSERT
INSERT INTO users (user_id, email, device_token)
VALUES (
    1,
    '',
    ''
);

SELECT
    user_id,
    email,
    device_token,
    created_at,
    updated_at
FROM users
ORDER BY user_id;

SELECT
    user_id,
    email,
    device_token,
    created_at,
    updated_at
FROM users
WHERE user_id = 1;

UPDATE users
SET
    email = '',
    device_token = ''
WHERE user_id = 1;

DELETE FROM users
WHERE user_id = 1;

-- =========================================================
-- CRUD - DEVICE
-- =========================================================

INSERT INTO device (
    device_token,
    platform,
    app_version
)
VALUES (
    '',
    'ANDROID',
    '1.0.0'
);

SELECT
    device_token,
    platform,
    app_version,
    created_at,
    updated_at
FROM device
ORDER BY created_at;

SELECT
    device_token,
    platform,
    app_version,
    created_at,
    updated_at
FROM device
WHERE device_token = '';

UPDATE device
SET
    platform = 'IOS',
    app_version = '1.1.0'
WHERE device_token = '';

DELETE FROM device
WHERE device_token = '';

-- =========================================================
-- CRUD - PREFERENCE
-- =========================================================

INSERT INTO preference (
    device_token,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time
)
VALUES (
    '',
    '',
    '',
    FALSE,
    15
);

SELECT
    preference_id,
    device_token,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time,
    created_at,
    updated_at
FROM preference
ORDER BY preference_id;

SELECT
    preference_id,
    device_token,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time,
    created_at,
    updated_at
FROM preference
WHERE preference_id = 1;

SELECT
    preference_id,
    device_token,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time,
    created_at,
    updated_at
FROM preference
WHERE device_token = '';

UPDATE preference
SET
    transport_type = 'SUBWAY',
    route_preference = 'LESS_WALKING',
    slow_pace = TRUE,
    max_walking_time = 8
WHERE device_token = '';

DELETE FROM preference
WHERE device_token = '';


-- =========================================================
-- USO DAS STORED PROCEDURES
-- =========================================================

CALL sp_create_device_preference(
    '',
    'ANDROID',
    '2.0.0',
    'TRAIN',
    'FEWER_TRANSFERS',
    TRUE,
    10
);

CALL sp_create_or_link_user(
    2,
    '',
    ''
);

CALL sp_upsert_preference(
    '',
    'SUBWAY',
    'LESS_WALKING',
    FALSE,
    20
);

-- =========================================================
-- CONSULTAS NAS VIEWS
-- =========================================================

SELECT * FROM vw_device_with_user;
SELECT * FROM vw_preference_with_user;
SELECT * FROM vw_user_full_profile;

