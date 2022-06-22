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
import NotFoundPage from "./components/error_pages/NotFoundPage";
import ForgotPasswordPage from "./components/ForgotPasswordPage";
import NewPasswordPage from "./components/NewPasswordPage";
import VacationHouseProfile from "@/components/VacationHouseProfile";
import RentingEntityPreview from "@/components/RentingEntityPreview";
import UserPreviewProfile from "@/components/user_profiles/UserProfilePreview"; 

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import PictureInput from 'vue-picture-input'
import ShipProfile from "@/components/ShipProfile";
import vueCountryRegionSelect from 'vue3-country-region-select'
import VueTelInput from 'vue3-tel-input';
import 'vue3-tel-input/dist/vue3-tel-input.css'
import './assets/main.css';
import './assets/common-profile.css';
import 'v-calendar/dist/style.css';
import "@vueform/slider/themes/default.css"
import VCalendar from 'v-calendar';
// import VacationHouseOwnerProfile from "@/components/user_profiles/vacation_house_owner/VacationHouseOwnerProfile"
// import VacationHouseOwnerInfo from "@/components/user_profiles/vacation_house_owner/VacationHouseOwnerInfo"
// import VacationHouseOwnerHome from "@/components/user_profiles/vacation_house_owner/VacationHouseOwnerHome"
// import ShipOwnerProfile from "@/components/user_profiles/ship_owner/ShipOwnerProfile";
// import ShipOwnerHome from "@/components/user_profiles/ship_owner/ShipOwnerHome";
// import ShipOwnerInfo from "@/components/user_profiles/ship_owner/ShipOwnerInfo";
import OwnerProfile from "@/components/user_profiles/owner_profiles/OwnerProfile";
import OwnerHome from "@/components/user_profiles/owner_profiles/OwnerHome";
import OwnerInfo from "@/components/user_profiles/owner_profiles/OwnerInfo";
import OwnerReservations from "@/components/user_profiles/owner_profiles/OwnerReservations";
import FirstLogin from "@/components/user_profiles/admin_profile/FirstLogin";
import AdminComplaints from "@/components/user_profiles/admin_profile/AdminComplaints";
import AdminEntities from "@/components/user_profiles/admin_profile/AdminEntities";
import AdminFee from "@/components/user_profiles/admin_profile/AdminFee";
import AdminHome from "@/components/user_profiles/admin_profile/AdminHome";
import AdminLoyalty from "@/components/user_profiles/admin_profile/AdminLoyalty";
import AdminProfile from "@/components/user_profiles/admin_profile/AdminProfile";
import AdminRegister from "@/components/user_profiles/admin_profile/AdminRegister";
import AdminReports from "@/components/user_profiles/admin_profile/AdminReports";
import AdminRequests from "@/components/user_profiles/admin_profile/AdminRequests";
import AdminReservations from "@/components/user_profiles/admin_profile/AdminReservations";
import AdminReviews from "@/components/user_profiles/admin_profile/AdminReviews";


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
    { name: "userProfilePreviewPage" , path: "/user/:username", component:UserPreviewProfile},
    {
        name: "userProfile",
        path: "/user",
        component: UserProfile,
        children: [{
                name: "ownerProfile",
                path: "/owner",
                component: OwnerProfile,
                children: [
                    { name: "ownerHome", path: "/ownerHome", component: OwnerHome },
                    { name: "ownerInfo", path: "/ownerInfo", component: OwnerInfo },
                    { name: "ownerReservations", path: "/ownerReservations", component: OwnerReservations }
                ]
            },
            {
                name: "clientProfile",
                path: "/clientProfile",
                component: ClientProfile
            },
            {
                name: "adminHome",
                path: "/adminHome",
                component: AdminHome,
                children: [
                    { name: "adminComplaints", path: "/adminHome", component: AdminComplaints },
                    { name: "adminEntities", path: "/adminHome", component: AdminEntities },
                    { name: "adminFee", path: "/adminHome", component: AdminFee },
                    { name: "adminLoyalty", path: "/adminHome", component: AdminLoyalty },
                    { name: "adminProfile", path: "/adminHome", component: AdminProfile },
                    { name: "adminRegister", path: "/adminHome", component: AdminRegister },
                    { name: "adminReports", path: "/adminHome", component: AdminReports },
                    { name: "adminRequests", path: "/adminHome", component: AdminRequests },
                    { name: "adminReservations", path: "/adminHome", component: AdminReservations },
                    { name: "adminReviews", path: "/adminHome", component: AdminReviews }
                ]
            },
            {
                name: "firstLogin",
                path: "/firstLogin",
                component: FirstLogin
            },
        ]
    },
    { name: "notFoundPage",path: '/:pathMatch(.*)*', component: NotFoundPage }
];

const router = new createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


createApp(App)
    .use(router)
    .use(Toaster, { position: 'top', duration: 2000 }).use(vueCountryRegionSelect).use(VueTelInput).use(VCalendar, {})
    .component('Datepicker', Datepicker).component('PictureInput', PictureInput)
    .mount('#app')