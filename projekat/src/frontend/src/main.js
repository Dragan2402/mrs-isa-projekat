import App from './App.vue';
import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import ClientProfile from "./components/ClientProfile";
import Toaster from '@meforma/vue-toaster';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import FishingClassProfile from './components/FishingClassProfile';
import HomePage from "./components/HomePage";
import LoginPage from "./components/LoginPage"
import RegistrationPage from "./components/RegistrationPage";
import VerificationPage from "./components/VerificationPage";
import ForgotPasswordPage from "./components/ForgotPasswordPage";
import NewPasswordPage from "./components/NewPasswordPage";
import VacationHouseProfile from "@/components/VacationHouseProfile";
import RentingEntityPreview from "@/components/RentingEntityPreview";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import PictureInput from 'vue-picture-input'
import ShipProfile from "@/components/ShipProfile";
import vueCountryRegionSelect from 'vue3-country-region-select'
import vue3StarRatings from "vue3-star-ratings";
import VueTelInput from 'vue3-tel-input';
import 'vue3-tel-input/dist/vue3-tel-input.css'
import './assets/main.css';
import './assets/common-profile.css';
import 'v-calendar/dist/style.css';
import "@vueform/slider/themes/default.css"
import VCalendar from 'v-calendar';
import UserProfile from "@/components/user_profiles/UserProfile"
// import VacationHouseOwnerProfile from "@/components/user_profiles/vacation_house_owner/VacationHouseOwnerProfile"
// import VacationHouseOwnerInfo from "@/components/user_profiles/vacation_house_owner/VacationHouseOwnerInfo"
// import VacationHouseOwnerHome from "@/components/user_profiles/vacation_house_owner/VacationHouseOwnerHome"
// import ShipOwnerProfile from "@/components/user_profiles/ship_owner/ShipOwnerProfile";
// import ShipOwnerHome from "@/components/user_profiles/ship_owner/ShipOwnerHome";
// import ShipOwnerInfo from "@/components/user_profiles/ship_owner/ShipOwnerInfo";
import OwnerProfile from "@/components/user_profiles/owner_profiles/OwnerProfile";
import OwnerHome from "@/components/user_profiles/owner_profiles/OwnerHome";
import OwnerInfo from "@/components/user_profiles/owner_profiles/OwnerInfo";
import AdminProfile from "@/components/user_profiles/admin_profile/AdminProfile";
import AdminInfo from "@/components/user_profiles/admin_profile/AdminInfo";


const routes = [
    { name: "homePage", path: "/", component: HomePage },
    { path: "/registrationPage", component: RegistrationPage },
    { name: "loginPage", path: "/loginPage", component: LoginPage },
    { name: "clientProfile", path: "/clientProfile", component: ClientProfile },
    { path: "/fishingClassProfile", component: FishingClassProfile },
    { name: "vacationHouseProfile", path: "/vacationHouseProfile", component: VacationHouseProfile },
    { name: "shipProfile", path: "/shipProfile", component: ShipProfile },
    { name: "rentingEntityPreview", path: "/rentingEntityPreview/rID:id$dT:displayType", component: RentingEntityPreview },
    { name: "verificationPage", path: "//verification/aTvHtI:customId", component: VerificationPage },
    { name: "newPasswordPage", path: "/newPassword/:token", component: NewPasswordPage },
    { name: "forgotPasswordPage", path: "/forgotPasswordPage", component: ForgotPasswordPage },
    {
        name: "userProfile",
        path: "/profile",
        component: UserProfile,
        children: [
            {
                name: "ownerProfile",
                path: "/profileO",
                component: OwnerProfile,
                children: [
                    { name: "ownerHome", path: "/profileO", component: OwnerHome },
                    { name: "ownerInfo", path: "/profileO", component: OwnerInfo }
                ]
            },
            {
                name: "clientProfile",
                path: "/profileC",
                component: ClientProfile
            },
            {
                name: "adminProfile",
                path: "/profileA",
                component: AdminProfile,
                children: [
                    {name: "adminInfo", path: "/profileA", component: AdminInfo}
                ]
            }
        ]
    }
];

const router = new createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


createApp(App)
    .use(router)
    .use(Toaster, { position: 'top', duration: 2000 }).use(vueCountryRegionSelect).use(VueTelInput).use(VCalendar, {})
    .component('Datepicker', Datepicker).component('PictureInput', PictureInput).component("vue3-star-ratings", vue3StarRatings)
    .mount('#app')