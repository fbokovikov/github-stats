# github-stats

Для запуска требуется создать файл ```/etc/secret.properties```

И добавить в него следующие проперти
```
github.host=
github.user=
github.password=
```

Доступные ручки:

```
GET /pullRequests
GET /branches
GET /branches/{login}
```


Чтобы поднять локально oracle:
```
docker-compose up -d
```

Чтобы остановить контейнер:

```
docker ps
docker stop <conteiner_id>
```