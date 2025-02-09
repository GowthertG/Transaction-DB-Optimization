# Transaction Data Generation for Clients

## Overview

This process generates and inserts synthetic transaction data into the `transactions` table in the PostgreSQL database, associating each transaction with a client.

## Steps

1. **Generate Transaction Data**:
   - For each transaction, a random date (from the current decade), amount, and client ID are generated.
   - The `Faker` library is used to generate random dates, and the amount is randomly chosen within a specified range.

2. **Retrieve Client IDs**:
   - The script fetches all valid `client_id`s from the `clients` table to link transactions to actual clients.

3. **Batch Insert**:
   - Transactions are inserted in batches to improve performance. The script commits the data in batches (e.g., 10,000 rows at a time) to reduce the number of database round trips.
   - The final batch is inserted after the loop completes.

4. **Performance Measurement**:
   - The total time taken for inserting the transactions is measured and reported.

## Benefits

- **Efficient Data Insertion**: Batch insertion minimizes the overhead of individual insert operations, improving performance.
- **Flexible Data Generation**: Randomized transaction dates, amounts, and client associations provide a diverse dataset for testing.

## Future Considerations

- Implement more advanced error handling and logging for better fault tolerance.
- Adjust the batch size dynamically based on system performance and memory limitations.
