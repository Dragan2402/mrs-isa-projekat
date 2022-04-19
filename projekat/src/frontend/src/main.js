import App from './App.vue';
import {createApp} from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import ClientProfile from "./components/ClientProfile";
import Toaster from '@meforma/vue-toaster';
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import FishingClassProfile from './components/FishingClassProfile';
import HomePage from "./components/HomePage";
import VacationHouseProfile from "@/components/VacationHouseProfile";
import RentingEntityPreview from "@/components/RentingEntityPreview";
import Datepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import 'bootstrap/dist/css/bootstrap.min.css';


const routes = [
    {path: "/", component: HomePage},
    {path: "/clientProfile", component: ClientProfile},
    {path: "/fishingClassProfile", component: FishingClassProfile},
    {name: "vacationHouses", path: "/vacationHouses", component: VacationHouseProfile},
    {name: "rentingEntityPreview",path:"/rentingEntityPreview/rID:id$dT:displayType", component: RentingEntityPreview}
];

const router = new createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


createApp(App)
    .use(router)
    .use(Toaster, {position: 'top', duration: 2000})
    .component('Datepicker', Datepicker)
    .mount('#app')

