# Course---Motors
Автоматизированная система магазина по продажам двигателей, мотоблоков, мотокультиваторов и др.(название БД - motors(двигатели))
 - Веб-приложение должно быть написано на Java, также должна быть создана БД (свой вариант приложу, мб поможет), можно использовать технологии Hibernate, Spring Web (MVC), MySql.
 - Веб-приложение должно содержать авторизацию, регистрацию, мультиязычность, есть роли:продавец, покупатель, директор, проработанную логику ролей.
 - Покупатель просматривает каталог товаров и делает заказы. 
 - Продавец просматривает заказы и меняет их статусы, также может просматривать товары.
 - Директор(менеджер-админ) делает отчетность по продажам за месяц/неделю/дни, также может менять статусы заказов.Также должны быть ссылки на поставщиков(номера и др.). Создание пользователей и товаров и др.
 - Посылать таблицы с отчетностью на принтер (такие функции: выручка/затраты по продажам/закупкам, итоговая закупка как отчетность за периоды, накладные в виде таблиц прихода и ухода, чек для покупки или партии). 

Есть пожелания по содержанию: 
 - в каталоге должны быть изображения товаров, т.е. при создании и изменении можно загружать различные фильтры каталогов
 - хорошая навигация по категориям товаров
 - нужна функция для подсчета остатков товаров
 - огика разных ролей (трех)
 - наличие промежуточной сущности "склад" для пометок учета товаров(хранение и наличие)
 - заявки продавца на склад для получения/заказа товаров
 - для пользователя корзина покупок.
 - Стиль в bootstrap
