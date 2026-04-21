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

CREATE TABLE IF NOT EXISTS profiles (
    profile_id      BIGINT PRIMARY KEY,
    email        VARCHAR(255) NOT NULL UNIQUE,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS profiles (
    profile_id        BIGSERIAL PRIMARY KEY,
    device_token   UUID UNIQUE,
    email          VARCHAR(255) UNIQUE,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_profile_device
        FOREIGN KEY (device_token) REFERENCES device(device_token)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS preference (
    preference_id       BIGSERIAL PRIMARY KEY,
    device_token        UUID NOT NULL UNIQUE,
    transport_type      transport_type_enum,
    route_preference    route_preference_enum,
    slow_pace           BOOLEAN NOT NULL DEFAULT FALSE,
    max_walking_time    INTEGER,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_preference_device
        FOREIGN KEY (device_token) REFERENCES device(device_token)
        ON DELETE CASCADE,

    CONSTRAINT chk_preference_max_walking_time
        CHECK (max_walking_time IS NULL OR max_walking_time >= 0)
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

CREATE INDEX IF NOT EXISTS idx_device_profile_id
    ON device (profile_id);

CREATE INDEX IF NOT EXISTS idx_preference_profile_id
    ON preference (profile_id);

CREATE INDEX IF NOT EXISTS idx_profiles_email
    ON profiles(email);

-- =========================================================
-- DROP DE VIEWS
-- =========================================================

DROP VIEW IF EXISTS vw_device_with_profile;
DROP VIEW IF EXISTS vw_preference_with_profile;
DROP VIEW IF EXISTS vw_profile_full_profile;

-- =========================================================
-- VIEWS
-- =========================================================

CREATE OR REPLACE VIEW vw_device_with_profile AS
SELECT
    u.profile_id,
    u.email,
    u.created_at AS profile_created_at,
    u.updated_at AS profile_updated_at,
    d.device_token,
    d.platform,
    d.app_version,
    d.created_at AS device_created_at,
    d.updated_at AS device_updated_at
FROM profiles u
INNER JOIN device d
    ON d.profile_id = u.profile_id;

CREATE OR REPLACE VIEW vw_preference_with_profile AS
SELECT
    u.profile_id,
    u.email,
    p.preference_id,
    p.transport_type,
    p.route_preference,
    p.slow_pace,
    p.max_walking_time,
    p.created_at,
    p.updated_at
FROM profiles u
INNER JOIN preference p
    ON p.profile_id = u.profile_id;

CREATE OR REPLACE VIEW vw_profile_full_profile AS
SELECT
    u.profile_id,
    u.email,
    u.created_at AS profile_created_at,
    u.updated_at AS profile_updated_at,
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
    p.updated_at AS preference_updated_at
FROM profiles u
LEFT JOIN device d
    ON d.profile_id = u.profile_id
LEFT JOIN preference p
    ON p.profile_id = u.profile_id;

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

DROP TRIGGER IF EXISTS trg_profiles_set_updated_at ON profiles;
DROP TRIGGER IF EXISTS trg_device_set_updated_at ON device;
DROP TRIGGER IF EXISTS trg_preference_set_updated_at ON preference;

CREATE TRIGGER trg_profiles_set_updated_at
BEFORE UPDATE ON profiles
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

DROP PROCEDURE IF EXISTS sp_create_profile_device_preference(
    BIGINT,
    VARCHAR,
    UUID,
    platform_enum,
    VARCHAR,
    transport_type_enum,
    route_preference_enum,
    BOOLEAN,
    INTEGER
);

CREATE OR REPLACE PROCEDURE sp_create_profile_device_preference(
    p_profile_id BIGINT,
    p_email VARCHAR,
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
    INSERT INTO profiles (profile_id, email)
    VALUES (p_profile_id, p_email)
    ON CONFLICT (profile_id)
    DO UPDATE SET
        email = EXCLUDED.email,
        updated_at = CURRENT_TIMESTAMP;

    INSERT INTO device (device_token, profile_id, platform, app_version)
    VALUES (p_device_token, p_profile_id, p_platform, p_app_version)
    ON CONFLICT (device_token)
    DO UPDATE SET
        profile_id = EXCLUDED.profile_id,
        platform = EXCLUDED.platform,
        app_version = EXCLUDED.app_version,
        updated_at = CURRENT_TIMESTAMP;

    INSERT INTO preference (
        profile_id,
        transport_type,
        route_preference,
        slow_pace,
        max_walking_time
    )
    VALUES (
        p_profile_id,
        p_transport_type,
        p_route_preference,
        COALESCE(p_slow_pace, FALSE),
        p_max_walking_time
    )
    ON CONFLICT (profile_id)
    DO UPDATE SET
        transport_type = EXCLUDED.transport_type,
        route_preference = EXCLUDED.route_preference,
        slow_pace = EXCLUDED.slow_pace,
        max_walking_time = EXCLUDED.max_walking_time,
        updated_at = CURRENT_TIMESTAMP;
END;
$$;

DROP PROCEDURE IF EXISTS sp_upsert_preference(
    BIGINT,
    transport_type_enum,
    route_preference_enum,
    BOOLEAN,
    INTEGER
);

CREATE OR REPLACE PROCEDURE sp_upsert_preference(
    p_profile_id BIGINT,
    p_transport_type transport_type_enum,
    p_route_preference route_preference_enum,
    p_slow_pace BOOLEAN,
    p_max_walking_time INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO preference (
        profile_id,
        transport_type,
        route_preference,
        slow_pace,
        max_walking_time
    )
    VALUES (
        p_profile_id,
        p_transport_type,
        p_route_preference,
        COALESCE(p_slow_pace, FALSE),
        p_max_walking_time
    )
    ON CONFLICT (profile_id)
    DO UPDATE SET
        transport_type = EXCLUDED.transport_type,
        route_preference = EXCLUDED.route_preference,
        slow_pace = EXCLUDED.slow_pace,
        max_walking_time = EXCLUDED.max_walking_time,
        updated_at = CURRENT_TIMESTAMP;
END;
$$;

-- =========================================================
-- CRUD - profileS
-- =========================================================

-- INSERT
INSERT INTO profiles (profile_id, email)
VALUES (1, 'usuario1@email.com');

-- READ ALL
SELECT
    profile_id,
    email,
    created_at,
    updated_at
FROM profiles
ORDER BY profile_id;

-- READ BY ID
SELECT
    profile_id,
    email,
    created_at,
    updated_at
FROM profiles
WHERE profile_id = 1;

-- UPDATE
UPDATE profiles
SET email = 'usuario1_novo@email.com'
WHERE profile_id = 1;

-- DELETE
DELETE FROM profiles
WHERE profile_id = 1;

-- =========================================================
-- CRUD - DEVICE
-- =========================================================

-- INSERT
INSERT INTO device (device_token, profile_id, platform, app_version)
VALUES (
    '550e8400-e29b-41d4-a716-446655440000',
    1,
    'ANDROID',
    '1.0.0'
);

-- READ ALL
SELECT
    device_token,
    profile_id,
    platform,
    app_version,
    created_at,
    updated_at
FROM device
ORDER BY created_at;

-- READ BY TOKEN
SELECT
    device_token,
    profile_id,
    platform,
    app_version,
    created_at,
    updated_at
FROM device
WHERE device_token = '550e8400-e29b-41d4-a716-446655440000';

-- READ BY profile
SELECT
    device_token,
    profile_id,
    platform,
    app_version,
    created_at,
    updated_at
FROM device
WHERE profile_id = 1;

-- UPDATE
UPDATE device
SET
    platform = 'IOS',
    app_version = '1.1.0'
WHERE device_token = '550e8400-e29b-41d4-a716-446655440000';

-- DELETE
DELETE FROM device
WHERE device_token = '550e8400-e29b-41d4-a716-446655440000';

-- =========================================================
-- CRUD - PREFERENCE
-- =========================================================

-- INSERT
INSERT INTO preference (
    profile_id,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time
)
VALUES (
    1,
    'BUS',
    'FASTEST',
    FALSE,
    15
);

-- READ ALL
SELECT
    preference_id,
    profile_id,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time,
    created_at,
    updated_at
FROM preference
ORDER BY preference_id;

-- READ BY ID
SELECT
    preference_id,
    profile_id,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time,
    created_at,
    updated_at
FROM preference
WHERE preference_id = 1;

-- READ BY profile
SELECT
    preference_id,
    profile_id,
    transport_type,
    route_preference,
    slow_pace,
    max_walking_time,
    created_at,
    updated_at
FROM preference
WHERE profile_id = 1;

-- UPDATE
UPDATE preference
SET
    transport_type = 'SUBWAY',
    route_preference = 'LESS_WALKING',
    slow_pace = TRUE,
    max_walking_time = 8
WHERE profile_id = 1;

-- DELETE
DELETE FROM preference
WHERE profile_id = 1;

-- =========================================================
-- USO DAS STORED PROCEDURES
-- =========================================================

-- CRIA OU ATUALIZA profile, DEVICE E PREFERENCE DE UMA VEZ
CALL sp_create_profile_device_preference(
    2,
    'usuario2@email.com',
    '123e4567-e89b-12d3-a456-426614174000',
    'ANDROID',
    '2.0.0',
    'TRAIN',
    'FEWER_TRANSFERS',
    TRUE,
    10
);

-- INSERE OU ATUALIZA PREFERENCE AUTOMATICAMENTE
CALL sp_upsert_preference(
    2,
    'SUBWAY',
    'LESS_WALKING',
    FALSE,
    20
);

-- =========================================================
-- CONSULTAS NAS VIEWS
-- =========================================================

SELECT * FROM vw_device_with_profile;
SELECT * FROM vw_preference_with_profile;
SELECT * FROM vw_profile_full_profile;

