package com.shashank.splitterexpensemanager.core

const val ROOM_DB: String = "RoomDb"
const val GROUP_ID: String = "GroupId"
const val PAYER_ID: String = "PayerId"
const val RECEIVER_ID: String = "ReceiverId"
const val SELECT_REPAY: String = "SelectRepay"
const val AMOUNT: String = "Amount"
const val EXPENSES_ID: String = "ExpensesId"
const val REPAY_ID: String = "RepayId"
const val PERSON_ID: String = "PersonId"
const val FRIEND_ID: String = "FriendId"
const val IS_PERSON_IN_GROUP: String = "is person in group"
const val PERSON: String = "Person"
const val CATEGORY: String = "Category"
const val GROUP_MEMBER: String = "GroupMember"
const val SHARED_PREFERENCES: String = "mySharedPreferences"
const val UPDATE_EXPENSES: String = "UpdateExpense"
const val UPDATE_REPAY: String = "UpdateRepay"
const val UPDATE_GROUP: String = "UpdateGroup"
const val UPDATE_FRIEND: String = "UpdateFriend"
const val MALE: String = "Male"
const val FEMALE: String = "Female"

object GroupTypeImages {
    const val TRIP_CREATE_GROUP: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Create%20group%2Ftrip%20png.png?alt=media&token=ad0d649b-f226-4e46-80bd-2a8da7505459"
    const val HOME_CREATE_GROUP: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Create%20group%2Fhome_rent_icon_png.png?alt=media&token=5e67677e-dd37-4feb-b0f1-c250ae956bdb"
    const val COUPLE_CREATE_GROUP: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Create%20group%2Fcouple%20png.png?alt=media&token=c1798a0a-1fd6-450a-a437-d5c9e184c682"
    const val OTHER_CREATE_GROUP: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Create%20group%2Fother%20png.png?alt=media&token=2c30c0f5-8cc7-49eb-8e96-160b7d2adb99"
}

object CreateGroupImages {
    const val GROUP_IMAGE_CREATE_GROUP: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Create%20group%2Fadd_image_png.png?alt=media&token=581d8955-3fee-4b9c-b1e4-ceacf86107a6"
}

object CategoryImages {
    const val CHILDCARE_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fchildcare_icon_png.png?alt=media&token=e3a96606-285d-4e03-a8c5-f237d391e45f"
    const val CLOTHING_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fclothing_icon_png.png?alt=media&token=c781f301-acee-4c65-aad5-e98c300906b0"
    const val DINING_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fdining_icon_png.png?alt=media&token=96e0662b-3ed3-4508-90e0-44ad1c9a182d"
    const val EDUCATION_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Feducation_icon_png.png?alt=media&token=9f9f54f3-feb7-4465-82f7-9d3ada3a464c"
    const val ELECTRONICS_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Felectronics_icon_png.png?alt=media&token=0e0bfc4c-5b29-4d8c-87f0-2e16bd98bfbb"
    const val FURNITURE_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Ffurniture_icon_png.png?alt=media&token=9afcb5e2-1641-4c8b-90a2-d05c637be81f"
    const val GAME_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fgame_icon_png.png?alt=media&token=56bdd9d6-b593-47fe-bd7c-800940528b4c"
    const val GIFT_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fgift_icon_png.png?alt=media&token=5a5e1a54-8bc9-49d6-89d2-382da0f69b37"
    const val GROCERIES_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fgroceries_icon_png.png?alt=media&token=5c6c1d97-fc33-4cab-9938-0632e9f6a03b"
    const val HOME_RENT: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fhome_rent_icon_png.png?alt=media&token=1e2b8e8b-7653-4606-a61b-db9b16974705"
    const val HOUSEHOLD_SUPPLIES: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fhousehold_supplies_png.png?alt=media&token=c1852e20-6fd5-4b2d-bc0a-196bad76bf99"
    const val INSURANCE_icon: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Finsurence_icon_ing.png?alt=media&token=c0a4832d-f41d-4d6e-a66f-11192fed0147"
    const val LIQUOR_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fliquor_icon_png.png?alt=media&token=18bf9f3f-f5d5-42ee-b875-a6d4861393e0"
    const val MAINTENANCE_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fmaintenance_icon_png.png?alt=media&token=ebadbd0e-4f93-475a-90b2-c1c6ebe807be"
    const val MEDICAL_EXPENSES_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fmedical%20expences_icon_png.png?alt=media&token=ad06ed5a-e499-4150-8251-064b5e5b3f2a"
    const val MORTGAGE_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fmortgage_icon_png.png?alt=media&token=01b63dd2-732f-4450-b4a7-2b36f0f0d8ab"
    const val MOVIE_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fmovie_icon_png.png?alt=media&token=766e45ad-0a4a-4447-b95c-bd426f06c765"
    const val MUSIC_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fmusic_icon_png.png?alt=media&token=73502d3f-7f79-4ebe-b4b8-3cd307f0c80d"
    const val PETS_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fpets_icon_png.png?alt=media&token=73373ebf-0e97-42cd-ad60-cc1a560a5e1f"
    const val SPORT_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Fsport_icon_png.png?alt=media&token=cea99c1f-cfee-4170-89d5-acbd3e9ef43f"
    const val TAXES_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Category%2Ftaxes_icon_png.png?alt=media&token=6ec71ae0-e753-434e-953c-406ec13fc0b6"
}

object TotalImages {
    const val TOTAL_FILTER: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Total%2Ffilter_icon_png.png?alt=media&token=fc1d24a3-14b7-4485-b77f-1e3328bd607a"
    const val TOTAL: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Total%2Ftotal_icon.png?alt=media&token=28544767-f9ab-4ace-9cc1-b0ee3698c7c2"
    const val TOTAL_YOU_PAID_FOR: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Total%2Ftotal_you_paid_for.png?alt=media&token=763129c8-4ee1-42d3-9cdd-e982cd702eda"
    const val YOUR_SHARE: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Total%2Fyour_share_icon.png?alt=media&token=e6299550-97b6-484b-9c9a-d40b893ee940"
}

object AddExpensesImages {
    const val RS_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Add%20Expenses%2FRS_icon.png?alt=media&token=118c889c-4f7a-4eab-99c2-d594159a8b20"
    const val CALENDAR_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Add%20Expenses%2Fcalendar_icon.png?alt=media&token=cd2999f0-50c6-41c8-9f06-87e10c1e1345"
    const val CATEGORY_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Add%20Expenses%2Fcategory_icon.png?alt=media&token=a73ab679-8282-46ff-9312-8260854ae1ec"
    const val CLOCK_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Add%20Expenses%2Fclock_icon.png?alt=media&token=497991ea-0be1-4d9e-af75-a7c19e3ece17"
    const val DESCRIPTION_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Add%20Expenses%2Fdescription_icon.png?alt=media&token=0e171955-b6f9-44c9-a6ad-7368b0d12849"
}

object CommonImages {
    const val USER_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fuser_image.png?alt=media&token=2197d632-f2d4-4878-8c3d-d67681dd5c5e"
    const val ARROW_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Farrow.png?alt=media&token=5a8abed8-1d08-4407-9473-e14b4cdfd2e2"
    const val CANCEL_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fcancel.png?alt=media&token=732be5de-4105-4d90-a030-2943d3e7293c"
    const val DELETE_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fdelete.png?alt=media&token=3fd528c7-68b1-4d79-9ccf-a276570017d5"
    const val FILTER_NORMAL_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Ffilter_normal.png?alt=media&token=7cb3b3f1-4777-4f7c-af7a-72fb29c4ad1b"
    const val FRIENDS_MEET_EACH_OTHER_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Ffriends_meet_each_other.png?alt=media&token=eda26a90-726a-4ada-be85-08cc1f1316dd"
    const val GIRL_CHILLING_OUT_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fgirl_chilling_out.png?alt=media&token=0b6b7ca1-4295-44b4-8dee-eaaf4cf51c16"
    const val SETTING_ICON: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fsetting_icon.png?alt=media&token=3c2c5813-2c73-4957-9bcc-2f31746d861f"
    const val ADD_NEW_GROUP: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fadd%20new%20group.png?alt=media&token=f971b4b4-8f61-400a-af19-84e8435cbe8b"
    const val MALE: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fmale.png?alt=media&token=3015241b-9015-4dd1-b16d-8367a7f9f7f4"

    const val FEMALE: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Ffemale.png?alt=media&token=90705933-35b8-4013-a884-787590d117fb"

    const val GIRL: String =
        "https://firebasestorage.googleapis.com/v0/b/splitter-expense-manager.appspot.com/o/" +
            "Common%2Fgir.png?alt=media&token=5cf52772-dc8c-466f-a535-5e5a09e6ea2d"
}