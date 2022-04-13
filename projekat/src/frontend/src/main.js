import App from './App.vue';
import { createApp } from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import HelloWorld from "./components/HelloWorld";
import TestPage from "./components/TestPage";
import ClientProfile from "./components/ClientProfile";
import Toaster from '@meforma/vue-toaster';
import VacationHouseProfile from "@/components/VacationHouseProfile";

const routes= [
    { path: "/" , component : HelloWorld},
    { path: "/testPage" , component : TestPage},
    { path: "/clientProfile" , component:ClientProfile},
    { path: "/vacationHouses", component: VacationHouseProfile}
];

const router = new createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
}) 

createApp(App).use(router).use(Toaster,{position: 'top',duration : 2000}).mount('#app')
