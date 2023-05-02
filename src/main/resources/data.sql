insert into app_user(id,username,password,role)
values ('8864387d-d43b-47fd-8f73-4e6acb217620','admin','$2a$10$MuRwWtb/8y889Y7SVRxh/.9Sdj8qD6WTZJq.6EDl3h.Paeyb5Wqje','ADMIN')
    on CONFLICT (username) DO NOTHING;