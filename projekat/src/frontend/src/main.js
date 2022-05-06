import App from './App.vue';
import {createApp} from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import ClientProfile from "./components/ClientProfile";
import Toaster from '@meforma/vue-toaster';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import FishingClassProfile from './components/FishingClassProfile';
import HomePage from "./components/HomePage";
import RegistrationPage from "./components/RegistrationPage";
import VacationHouseProfile from "@/components/VacationHouseProfile";
import RentingEntityPreview from "@/components/RentingEntityPreview";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import PictureInput from 'vue-picture-input'
import ShipProfile from "@/components/ShipProfile";
import vueCountryRegionSelect from 'vue3-country-region-select'
import VueTelInput from 'vue3-tel-input'
import 'vue3-tel-input/dist/vue3-tel-input.css'
import './assets/main.css'; //main css


const routes = [
    {path: "/", component: HomePage},
    {path: "/registrationPage", component:RegistrationPage},
    {path: "/clientProfile", component: ClientProfile},
    {path: "/fishingClassProfile", component: FishingClassProfile},
    {name: "vacationHouseProfile", path: "/vacationHouseProfile", component: VacationHouseProfile},
    {name: "shipProfile", path: "/shipProfile", component: ShipProfile},
    {name: "rentingEntityPreview",path:"/rentingEntityPreview/rID:id$dT:displayType", component: RentingEntityPreview}
];

const router = new createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


createApp(App)
    .use(router)
    .use(Toaster, {position: 'top', duration: 2000}).use(vueCountryRegionSelect).use(VueTelInput)
    .component('Datepicker', Datepicker).component('PictureInput',PictureInput)
    .mount('#app')

