package ir.enigma.app.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import ir.enigma.app.R
import ir.enigma.app.ui.theme.*

enum class PurchaseCategory(
    val text: String,
    val categoryGroup: PurchaseCategoryGroup,
    @DrawableRes val iconRes: Int,
) {

    Rent(
        "اجاره خانه", PurchaseCategoryGroup.Home, R.drawable.ic_fill_home,
    ),

    Cleaning(
        "شستشو", PurchaseCategoryGroup.Home, R.drawable.ic_fill_cleaning,
    ),

    Phone(
        "تلفن", PurchaseCategoryGroup.Home, R.drawable.ic_fill_phone,
    ),

    Internet(
        "اینترنت", PurchaseCategoryGroup.Home, R.drawable.ic_fill_wifi,
    ),

    Repairs(
        "تعمیرات", PurchaseCategoryGroup.Home, R.drawable.ic_fill_support,
    ),

    ElectricBill(
        "قبض برق", PurchaseCategoryGroup.Home, R.drawable.ic_fill_electricity,
    ),

    GasBill(
        "قبض گاز", PurchaseCategoryGroup.Home, R.drawable.ic_fill_gas_stove,
    ),

    WaterBill(
        "قبض آب", PurchaseCategoryGroup.Home, R.drawable.ic_fill_faucet,
    ),

    FastFood(
        "فست\u200Cفود", PurchaseCategoryGroup.Food, R.drawable.ic_fill_snack,
    ),

    WarmFood(
        "غذای گرم", PurchaseCategoryGroup.Food, R.drawable.ic_fill_food_tray,
    ),

    SoftDrink(
        "نوشابه", PurchaseCategoryGroup.Food, R.drawable.ic_soft_drink,
    ),

    PreFood(
        "پیش غذا", PurchaseCategoryGroup.Food, R.drawable.ic_soup,
    ),

    Juice(
        "آبمیوه", PurchaseCategoryGroup.Food, R.drawable.ic_food_juice,
    ),

    IceCream(
        "بستنی", PurchaseCategoryGroup.Food, R.drawable.ic_fill_ice_cream,
    ),

    BirthDayCake(
        "کیک تولد", PurchaseCategoryGroup.Food, R.drawable.ic_fill_birthday_cake,
    ),

    Vegetables(
        "میوه یا سبزیجات", PurchaseCategoryGroup.Food, R.drawable.ic_fill_vegtables,
    ),

    Spice(
        "ادویه", PurchaseCategoryGroup.Food, R.drawable.ic_fill_ice_cream,
    ),

    Diary(
        "لبنیات", PurchaseCategoryGroup.Food, R.drawable.ic_diary,
    ),

    TeaOrCoffee(
        "قهوه یا چای", PurchaseCategoryGroup.Food, R.drawable.ic_cofee,
    ),

    JunkFood(
        "تنقلات", PurchaseCategoryGroup.Food, R.drawable.ic_snack_chips,
    ),

    BusTicket(
        "بلیط اتوبوس", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_bus,
    ),

    TrainTicket(
        "بلیط قطار", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_train,
    ),

    AirPlaneTicket(
        "بلیط هواپیما", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_plane,
    ),

    Fuel(
        "سوخت", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_gas_station,
    ),

    Taxi(
        "تاکسی", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_taxi_front_view,
    ),

    Park(
        "پارکینگ", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_parking,
    ),

    Hotel(
        "هتل", PurchaseCategoryGroup.Transportation, R.drawable.ic_fill_hotel,
    ),

    MovieTicket(
        "بلیط سینما", PurchaseCategoryGroup.Entertainment, R.drawable.ic_fill_ticket,
    ),

    Sport(
        "ورزش", PurchaseCategoryGroup.Entertainment, R.drawable.ic_fill_ball,
    ),

    Music(
        "موسیقی", PurchaseCategoryGroup.Entertainment, R.drawable.ic_fill_headphones,
    ),

    Game(
        "بازی", PurchaseCategoryGroup.Entertainment, R.drawable.ic_fill_gamepad,
    ),

    Gift(
        "هدیه", PurchaseCategoryGroup.Other, R.drawable.ic_fill_gift_box_with_a_bow,
    ),

    Education(
        "آموزش", PurchaseCategoryGroup.Other, R.drawable.ic_fill_mortarboard,
    ),

    Cloth(
        "لباس", PurchaseCategoryGroup.Other, R.drawable.ic_fill_clothes_hanger,
    ),

    Health(
        "سلامت", PurchaseCategoryGroup.Other, R.drawable.ic_fill_cardiogram,
    ),

    Other(
        "سایر", PurchaseCategoryGroup.Other, R.drawable.ic_fill_invoice,
    ),


}

enum class PurchaseCategoryGroup(val text: String, val color: Color) {
    Home("سکونتگاه", category_color_1),
    Food("غذا و خوراکی", category_color_2),
    Transportation("حمل و نقل و سفر", category_color_3),
    Entertainment("بازی و سرگرمی", category_color_4),
    Other("سایر", category_color_5)
}