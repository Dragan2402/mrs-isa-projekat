<template>
  <nav class="navbar" >
    <div class="navbar-top">
      <div class="nav-left">
        <img class="brand" @click="jumpToHomePage()" src="@/assets/app-logo.png">
      </div>
      <div class="nav-right" v-if="this.signedIn==false">
        <button type="button" v-if="signIn" @click="jmpToLoginPage()" class="btn btn-warning" >Sign in</button>
        <button type="button"  v-if="signUp" @click="jmpToRegistrationPage()"  class="btn btn-warning">Register</button>
      </div>
      <div class="nav-right" v-else>
        <img v-bind:src="'data:image/jpeg;base64,' + loggedPicture" style="width: 50px; height: 40px;">
        {{loggedUser.username}}
        <button type="button"  @click="logout()" class="btn btn-warning" >Logout</button>
      </div>
    </div>
    
  </nav>
  <router-view></router-view>
</template>

<script>
import axios from "axios"
export default {
  name: "App",
  data() {
    return {
      signIn: true,
      signUp: true,
      signedIn: false,
      loggedUser: {},
      loggedPicture:null,
      accessToken: null,
    }
  }, 
  mounted(){    
     if(localStorage.getItem("jwt")!=null){
        this.signIn=true;
        this.signUp=true;
        this.signedIn=false;
      }else{
        this.signIn=false;
        this.signUp=false;
        this.signedIn=true;        
        axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {this.loggedUser=response.data});
        axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.loggedPicture=response.data));
      }
  },   
  methods:{
    jumpToHomePage(){
      this.$router.push("/");
      this.signIn=true;
      this.signUp=true;
    },
    jmpToRegistrationPage(){
      this.$router.push("/registrationPage")
    },
    jmpToLoginPage(){
      this.$router.push("/loginPage")
    },
    logout(){
      this.accessToken=null;
      localStorage.setItem("jwt",null);          
      window.location.href = 'http://localhost:3000/';
     
    }
  }
  
}
</script>

<style>

nav {
  background: #585858;
}

.navbar-top {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  margin: auto;
  width: 60%;
  padding-bottom: 5px;
  }

.brand {
  width: 60px;
  margin-left: 10px;
  }

.navbar-top button {
  margin-top:3px;
  margin-inline: 10px;
  }

.navbar-top .brand:hover {
  cursor: pointer;
  -webkit-filter: brightness(80%);
}

</style>
