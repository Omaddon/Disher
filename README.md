# DISHER

Practice for KC - Android Basics.

An application for android in kotlin to handle the orders of the tables of a restaurant. Multidevice: mobile and tablets.

## Structure

### Acitivities
- ***TableListActivity***: MAIN-ACTIVITY. Manage what to show, DishListActivity or 2 fragments (DishListFragment and TableListFragment). Implementes multiple interfaces in order to record the move on the DishList, knows what to show in DishList, etc.
- ***TableBillActivity***: an auxiliar view to show the bill of a table. You can "pay" the bill and delete all dishes for the table. The only option price format is in euros.
- ***DishListActivity***: show the list of dishes for the table selected. Launch a fragment to do this (DishListFragment). As TableListActivity, implements mutiple interfaces.
- ***DishesAvailableActivity***: a view to show the dishes availables for our restaurant. This list will be downloaded from a fake api (mock.io, see below) in TableListActivity when the app starts.
- ***DishDetailActivity***: a view to show dish detail when touch a dish on our dish list. In this view we can delete the dish from the table.
- ***AddDishDetailActivity***: a view to add a dish to table. Shows dish details and one edit box to set options for this dish.

### Fragments
- ***DishListFragment***: shows dishes list for the selected table. Containers: TableListActivity (400dp-landscape) or DishListActivity (400dp-portrait).
- ***TableListFragment***: show the table list for our restaurant. Containers: TableListActivity.

### Models
- ***Tables***: list of tables.
- ***Table***: model with name and an optional list of dishes. Has some methods for add/delete dishes.
- ***Dish***: model of a dish.
- ***Allergen***: enum for our posibles allergen.
- ***DishesAvailable***: objetc with the list of dishes availabes for our restaurant. This list will be downloaded when the app starts.

### Adapters
- ***DishRecyclerViewAdapter***: adapter for our dish list.
- ***DishesAvailableRecyclerViewAdapter***: adapter for our dishes availables list.

## View expamples from app
Dish List | Dishes Available | Dish Detail | Table Bill | Landscape
------------ | ------------ | ------------ | ------------ | ------------
<img src="https://github.com/Omaddon/Disher/blob/master/snapshots/dish_list.png" width="150px"> | <img src="https://github.com/Omaddon/Disher/blob/master/snapshots/dishes_available.png" width="150px"> | <img src="https://github.com/Omaddon/Disher/blob/master/snapshots/dish_detail.png" width="150px"> | <img src="https://github.com/Omaddon/Disher/blob/master/snapshots/table_bill.png" width="150px"> | <img src="https://github.com/Omaddon/Disher/blob/master/snapshots/landscape.png" width="250px">

## Navigation

- When app starts, the dish available list will be downloaded from a mock api: [MockResponse](http://www.mocky.io/v2/5a0b2455320000ed01e96398)
- Depending on the screen size of the device and its orientation, a screen or other initial screen will be displayed.

#### 400dp - landscape
- On the left of screen we can see the table list. On right, dish list for the table selected. A plus floting button on bottom right. Menu with only one option: the bill for the selected table.
- If you tap on a dish from the dish list, the app navigates to dish detail view, where you can delete the this dish from the table or just back to the previous view.
- If you tap on floating button, dishes available list will be shown. In this view you can tap on any dish to navigate to dish detail, where you can add the dish, edit options for the dish or just back to the dish available list again.
- If you select bill option menu, a new view will show you the bill for the current table. In this view you can "pay" the de bill and delete all dishes from the table, or just back to the previous view.

#### 400dp - portrait
- Dish list for the table selected.
- If you tap on a dish from the dishes list, the app navigates to dish detail view, where you can delete the this dish from the table or just back to the previous view.
- If you tap on floating button, dishes available list will be shown. In this view you can tap on any dish to navigate to dish detail, where you can add the dish, edit options for the dish or just back to the dish available list again.
- If you select bill option menu, a new view will show you the bill for the current table. In this view you can "pay" the de bill and delete all dishes from the table, or just back to the previous view.

#### Other cases
- Table list with all tables of our restaurant.
- If you tap on a table from the table list, dishes list for the table selected view will be show.
- If you tap on a dish from the dishes list, the app navigates to dish detail view, where you can delete the this dish from the table or just back to the previous view.
- If you tap on floating button, dishes available list will be shown. In this view you can tap on any dish to navigate to dish detail, where you can add the dish, edit options for the dish or just back to the dish available list again.
- If you select bill option menu, a new view will show you the bill for the current table. In this view you can "pay" the de bill and delete all dishes from the table, or just back to the previous view.

## Response from Mock.io
```
{ "dishes" : [
    {
      "name": "Cerveza",
      "image": 0,
      "allergen": [0, 10, 11],
      "price": 2.0,
      "description": "Manjar de dioses. Tan antigua como la humanidad. Nada puede compararse a un buen trago de cerveza!!"
    },
    {
      "name": "Ensalada Caesar",
      "image": 1,
      "allergen": [9, 10, 13],
      "price": 7.50,
      "description": "La mejor ensalada del chef para los paladares exquisitos."
    }
  ]
}
```

---
###### © Miguel J.