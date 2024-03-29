# Управління веб сервером

## Технічне завдання
[Product Requirements Document](https://docs.google.com/document/d/12EyGayzLKw21L1KkU0q2yTmxEErpBGQnZFWEQYRMtHI/edit?usp=share_link)

## Проблема
Angular використовує Webpack dev server, який запускається при розробці, і при зміні файлів проекту він автоматично підхоплює зміни і на гярячу оновлює веб сервер. Наприклад така помилка як 'XXX is not a known element' означає що такого елемента не існує, або неправильно вказані імпорти/експорти, хоча якщо все добре прописано, але ця помилка появляється це вже дивно(таке стається досить часто при створенні нових файлів, і є добре відомою проблемою Angular) . Єдиним рішенням є зайти в термінал, зупинити сервер і знову його запустити щоб він правильно підхопив усі файли для збірки.

## Запропоноване рішення
Проект буде складатися з двох частин:
- Arduino з дисплеєм, енкодером і двома кнопками яка спілкується з компютером по COM порту. 
- Сервіс демон який слухає COM порт і виконує дії в залежності від отриманої комнади.

## Опис
Arduino повинна відображати список проектів, обирати проект зі списку, відображати статус, надсилати запити до сервіса про запуск проекту, зупинку проекту, перезапуск проекту.

- Відображенння списку проектів - сервіс буде шукати на компютері Angular проекти. Ці проекти будуть передаватися на Arduino по СОМ порту, яка в свою чергу відобразить їх на дисплеї
- Обирати проект зі списку - за допомогою енкодера переміщуватися по списку проектів і при натисканні на кнопку енкодера встановити вибраний проект як активний
- Запуск проекту - надіслати запит до сервіса про запуск вибраного проекту, для цього потрібно з кореня директорії проекту виконати команду ng serve що запустить dev сервер
- Зупинка проекту -  надіслати запит до сервіса про зупинку вибраного проекту, для цього потрібно знайти pid проекту, за допомогою команди `netstat -tlnp`. Після того як pid знайдено можна вбити процес
- Перезапуск проекту - надіслати запит до сервіса про перезапуск вибраного проекту, коли в консолі розробника появляються дивні помилки то по натисканні кнопки активний проект буде зупинятися і запускатися знову.
- Відображення статусу - після вибору активного проекту на дисплеї буде відображатися назва активного проекту та його статус.
