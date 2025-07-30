-- ====================
-- USUARIOS (tabla "user")
-- ====================
INSERT INTO `user` (id, username, password, role) VALUES
  (1, 'admin',    '{bcrypt}$2a$10$7H3jF...QwX', 'ADMIN'),
  (2, 'alice',    '{bcrypt}$2a$10$yL8kZ...PpR', 'ACCOUNTHOLDER'),
  (3, 'bob',      '{bcrypt}$2a$10$Nf2mV...TzS', 'ACCOUNTHOLDER'),
  (4, 'charlie',  '{bcrypt}$2a$10$Wk5nM...XyB', 'ACCOUNTHOLDER'),
  (5, 'thirdp',   '{bcrypt}$2a$10$Qa1dH...LmN', 'THIRD_PARTY');

-- =================================
-- ACCOUNT HOLDERS (tabla account_holder)
-- =================================
INSERT INTO account_holder (id, name, username, date_of_birth) VALUES
  (1, 'Alice Smith',   'alice',   '1985-06-15'),
  (2, 'Bob Johnson',   'bob',     '1992-11-03'),
  (3, 'Charlie Díaz',  'charlie', '2000-02-20');

-- ===========================
-- CHECKING (tabla checking)
-- ===========================
INSERT INTO checking (
    id,
    balance_amount,
    balance_currency,
    secret_key,
    minimum_balance,
    monthly_maintenance_fee,
    creation_date,
    status,
    primary_owner_id,
    secondary_owner_id
) VALUES
  (1,  500.00, 'EUR', 'sekret1', 250.00, 12.00, '2025-07-01', 'ACTIVE', 1, NULL),
  (2, 1200.50, 'EUR', 'sekret2', 250.00, 12.00, '2025-06-20', 'ACTIVE', 2,   3),
  (3,  300.00, 'EUR', 'sekret3', 250.00, 12.00, '2025-05-10', 'FROZEN',1,   2);

-- =================================
-- STUDENT CHECKING (tabla student_checking)
-- =================================
INSERT INTO student_checking (
    id,
    balance_amount,
    balance_currency,
    secret_key,
    creation_date,
    status,
    primary_owner_id,
    secondary_owner_id
) VALUES
  (4, 150.00, 'EUR', 'stud1', '2025-07-15', 'ACTIVE', 3, NULL),
  (5,  50.00, 'EUR', 'stud2', '2025-07-20', 'ACTIVE', 2,   1);

-- ===========================
-- SAVINGS (tabla savings)
-- ===========================
INSERT INTO savings (
    id,
    balance_amount,
    balance_currency,
    minimum_balance,
    interest_rate,
    last_interest_date,
    creation_date,
    status,
    primary_owner_id,
    secondary_owner_id
) VALUES
  (6, 2000.00, 'EUR', 1000.00, 0.0025, '2024-07-29', '2024-07-29', 'ACTIVE', 1, NULL),
  (7,  800.00, 'EUR', 1000.00, 0.0025, '2024-07-29', '2024-07-29', 'ACTIVE', 3,   2);

-- ===========================
-- CREDIT CARD (tabla credit_card)
-- ===========================
INSERT INTO credit_card (
    id,
    balance_amount,
    balance_currency,
    credit_limit,
    interest_rate,
    last_interest_date,
    creation_date,
    status,
    primary_owner_id,
    secondary_owner_id
) VALUES
  (8, 300.00, 'EUR', 1000.00, 0.15, '2025-06-01', '2025-01-01', 'ACTIVE', 2, NULL),
  (9,   0.00, 'EUR',  500.00, 0.15, '2025-06-15', '2025-02-01', 'ACTIVE', 3,   1);

-- ===========================
-- THIRD PARTY
-- ===========================
INSERT INTO third_party (id, name, hashed_key) VALUES
  (1, 'PayrollService', 'a1b2c3d4-e5f6-4712-89ab-cdef01234567'),
  (2, 'PaymentGateway', 'f6e5d4c3-b2a1-6789-4321-fedcba987654');
