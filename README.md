# SOA-LAB-3-Service-1

Изменения в "вызываемом" сервисе:

Сконфигурировать окружение для работы сервиса на платформе Spring Boot.
Запустить второй экземпляр сервиса на другом порту. Реализовать балансировку нагрузки между экземплярами с помощью Haproxy.
Реализовать механизм Service Discovery. Для этого установить Consul и интегрировать свой сервис с ним, автоматически регистрируя в момент запуска.

Запрос для постмэн 

curl --location 'http://localhost:7000'

scripts
for (let i = 0; i < 10; i++) {
    pm.sendRequest("http://localhost:7000", function (err, response) {
        console.log(`Request ${i + 1}:`, response.status);
    });
}
