# Database Design for Transaction Management

## Database Overview

The database is designed to store client information and their associated transactions. It includes two main tables: `clients` and `transactions`.

## Tables

### 1. `clients` Table

This table stores information about the clients.

| Column Name  | Data Type    | Description                     |
|--------------|--------------|---------------------------------|
| `client_id`  | `SERIAL`     | Primary Key, Auto-incrementing ID |
| `name`       | `VARCHAR(100)`| Client's full name             |
| `email`      | `VARCHAR(100)`| Client's email address         |

### 2. `transactions` Table

This table stores information about each transaction.

| Column Name  | Data Type    | Description                     |
|--------------|--------------|---------------------------------|
| `id`         | `SERIAL`     | Primary Key, Auto-incrementing ID |
| `date`       | `DATE`       | Transaction date                |
| `montant`    | `DECIMAL(10, 2)`| Transaction amount            |
| `client_id`  | `INT`        | Foreign Key referencing `client_id` from the `clients` table |

### 3. Foreign Key Relationship

- The `transactions` table has a foreign key constraint on the `client_id` column, linking it to the `client_id` in the `clients` table.
- **Action on delete:** `ON DELETE CASCADE` — If a client is deleted, all their transactions will also be removed automatically.

## SQL Commands

### 1. Create `clients` Table

```sql
CREATE TABLE clients (
    client_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);
```

### 2. Create `transactions` Table

```sql
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    date DATE,
    montant DECIMAL(10, 2),
    client_id INT,
    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE
);
```

## Future Improvements

- **Indexing:** Consider adding indexes on frequently queried columns (e.g., `client_id` in the `transactions` table) for improved performance.
- **Partitioning:** Explore partitioning strategies for the `transactions` table if dealing with large datasets over time.
