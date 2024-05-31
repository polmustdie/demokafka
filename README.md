# AnomalyDetectorGeo
Дипломная работа бакалавра на тему: "Сервис обнаружения аномалий в геоданных в потоковом и пакетном режимах". 
## Содержание
- [Технологии](#технологии)
- [Использование](#использование)
- [Требования](#требования)
- [Запросы](#запросы)

## Технологии
- Java
- SpringBoot
- Apache Kafka
- PostgreSQL
- ClickHouse
- Prometheus
- Grafana
- Weka
- Docker

## Требования
- Docker v.26.0.0

## Использование
Скачайте .jar файл по ссылке *ссылка-нейм*.

Соберите необходимые образы и поднимите docker-контейнеры командой:
```sh
$ docker-compose up -d
```

## Запросы
- Запрос для потоковой обработки:
![image](https://github.com/polmustdie/demokafka/assets/89970688/38d44c64-7d81-41c6-803c-4e1d816e9ddb)

- Запрос для пакетной обработки:
  ![image](https://github.com/polmustdie/demokafka/assets/89970688/e8ae841c-373d-4064-b285-8f7050a0235a)


где mode принимает значения от 0 до 4:
- 0 – LOF
- 1 – DBSCAN
- 2 – GaussBased
- 3 – HilOut
- 4 - IsolationForest
