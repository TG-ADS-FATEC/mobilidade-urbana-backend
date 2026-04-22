-- Database: db_urban_mobility

CREATE EXTENSION IF NOT EXISTS postgis;

-- =========================
-- ENUMS
-- =========================

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

-- =========================
-- TABELAS PRINCIPAIS
-- =========================

CREATE TABLE IF NOT EXISTS agency (
    agency_id       BIGINT PRIMARY KEY,
    agency_name     VARCHAR(255) NOT NULL,
    agency_url      VARCHAR(500),
    agency_time_zone VARCHAR(100),
    agency_language VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS route (
    route_id            BIGINT PRIMARY KEY,
    agency_id           BIGINT NOT NULL,
    route_short_name    VARCHAR(100),
    route_long_name     VARCHAR(255),
    route_type          INTEGER,
    route_color         VARCHAR(20),
    route_text_color    VARCHAR(20),

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
    frequency_id      BIGSERIAL PRIMARY KEY,
    trip_id           BIGINT NOT NULL,
    start_time        TIME NOT NULL,
    end_time          TIME NOT NULL,
    headway_seconds   INTEGER NOT NULL,

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

-- =========================
-- TABELAS DE USUÁRIO
-- =========================

CREATE TABLE IF NOT EXISTS users (
    user_id    BIGINT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS device (
    device_token   UUID PRIMARY KEY,
    user_id        BIGINT NOT NULL UNIQUE,
    platform       platform_enum NOT NULL,
    app_version    VARCHAR(50),
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_device_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS preference (
    preference_id      BIGINT PRIMARY KEY,
    user_id            BIGINT NOT NULL UNIQUE,
    transport_type     transport_type_enum,
    route_preference   route_preference_enum,
    slow_pace          BOOLEAN DEFAULT FALSE,
    max_walking_time   INTEGER,
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_preference_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- =========================
-- ÍNDICES
-- =========================

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