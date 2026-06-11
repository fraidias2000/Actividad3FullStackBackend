INSERT INTO roles (name, description)
VALUES
    ('CUSTOMER', 'Cliente de la tienda'),
    ('ADMIN', 'Administrador de la aplicación')
ON CONFLICT (name) DO NOTHING;

INSERT INTO users (
    email,
    password,
    first_name,
    last_name,
    phone,
    enabled,
    account_non_locked
)
VALUES
    (
        'admin@relatosdepapel.com',
        '$2a$10$.yTw2J14EAoWLDf4zr/bOOxKADfjr5Q2BN1FxByNV09Go93T0qUAi', --contraseña admin123
        'Admin',
        'Relatos',
        '600000000',
        TRUE,
        TRUE
    ),
    (
        'cliente@relatosdepapel.com',
        '$2a$10$.yTw2J14EAoWLDf4zr/bOOxKADfjr5Q2BN1FxByNV09Go93T0qUAi',--contraseña admin123
        'Cliente',
        'Demo',
        '611111111',
        TRUE,
        TRUE
    )
ON CONFLICT (email) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@relatosdepapel.com'
  AND r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'cliente@relatosdepapel.com'
  AND r.name = 'CUSTOMER'
ON CONFLICT DO NOTHING;