# Учебно-исследовательская работа

Данный проект реализует первую часть решения задачи MDVRPTW (задача маршрутизации транспорта с множеством депо и временными окнами).
На данной стадии программа генерирует тестовый пример (4 депо, 16 клиентов): матрицу расстояний. 

Сначала генерируются расстояния от каждого депо до каждого клиента, затем аналогичная операция происходит для всех клиентов.
После этого для каждого объекта создаются временные окна (указаны в минутах от 0 до 1439, где 0 = 00:00, 1439 = 23:59)

После инициализации тестовых данных начинается процесс кластеризации клиентов методом разбиения вокруг медоидов (PAM, Partitioning 
Around Medoids). Результатом является вывод списка депо и привязанных к ним клиентов.


