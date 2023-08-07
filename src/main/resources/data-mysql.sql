INSERT INTO members (member_id, reward_summary)
VALUES (1, 0),
       (2, 0),
       (3, 10),
       (4, 20);

INSERT INTO rewards (reward_id, member_id, balance, initial_price, created_time)
VALUES (1, 1, 0, 10, 2023-02-26 05:01:47),
       (2, 1, -10, -10, 2023-02-26 05:01:47),
       (3, 3, 10, 10, 2023-02-26 05:01:47),
       (4, 4, 20, 30, 2023-02-26 05:01:47),
       (5, 4, -10, -10, 2023-02-26 05:01:47);

INSERT INTO history (history_id, member_id, price, created_time, reward_type)
VALUES (1, 1, 10, 2023-02-26 05:01:47, SAVE),
       (2, 1, 10, 2023-02-26 05:01:47, SPEND),
       (3, 3, 10, 2023-02-26 05:01:47, SAVE),
       (4, 4, 30, 2023-02-26 05:01:47, SAVE),
       (5, 4, 10, 2023-02-26 05:01:47, SPEND);