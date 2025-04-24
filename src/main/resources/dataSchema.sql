CREATE DATABASE spring_project;

-- Have to run this first
CREATE EXTENSION "uuid-ossp";

CREATE TABLE achievements
(
    achievement_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    title          varchar(50)  NOT NULL,
    description    varchar(100) NOT NULL,
    badge          varchar(100) NOT NULL,
    xp_required    int          NOT NULL
);

CREATE TABLE app_users
(
    app_user_id   uuid      DEFAULT uuid_generate_v4() PRIMARY KEY,
    username      varchar(50)                         NOT NULL,
    email         varchar(50)                         NOT NULL,
    password      varchar(250)                         NOT NULL,
    level         int       DEFAULT 0                 NOT NULL,
    xp            int       DEFAULT 0                 NOT NULL,
    profile_image varchar,
    is_verified   bool      DEFAULT FALSE,
    created_at    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL
);


CREATE TABLE app_user_achievements
(
    app_user_achievement_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    app_user_id             uuid REFERENCES app_users (app_user_id) ON DELETE CASCADE,
    achievement_id          uuid REFERENCES achievements (achievement_id) ON DELETE CASCADE,
    UNIQUE (app_user_id, achievement_id)

);

CREATE TABLE habits
(
    habit_id    uuid                  DEFAULT uuid_generate_v4() PRIMARY KEY,
    title       varchar(50)  NOT NULL,
    description varchar(100) NOT NULL,
    frequency   varchar(25)  NOT NULL,
    is_active   bool         NOT NULL DEFAULT TRUE,
    app_user_id uuid REFERENCES app_users (app_user_id) ON DELETE CASCADE,
    created_at  timestamp             DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE habit_logs
(
    habit_log_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    log_date     timestamp   NOT NULL,
    status       varchar(25) NOT NULL,
    xp_earned    int         NOT NULL,
    habit_id     uuid REFERENCES habits (habit_id) ON DELETE CASCADE
);

INSERT INTO achievements (title, description, badge, xp_required)
VALUES
    ('Getting Started', 'Complete your first task', 'badge_starter.png', 100),
    ('Level Up', 'Reach level 2', 'badge_level_up.png', 200),
    ('Consistency King', 'Log habits for 7 consecutive days', 'badge_consistency.png', 500),
    ('Master of Habits', 'Complete 50 habits', 'badge_master.png', 1000),
    ('XP Collector', 'Earn 1000 XP', 'badge_xp_collector.png', 1000);
