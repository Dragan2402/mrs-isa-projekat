
import App from './App.vue';
import { createApp } from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import HelloWorld from "./components/HelloWorld";
import TestPage from "./components/TestPage";




const routes= [
    { path: "/" , component : HelloWorld},
    { path: "/testPage" , component : TestPage},
];

const router = new createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
}) 

createApp(App).use(router).mount('#app')