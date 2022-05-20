<template>
  <nav class="navbar" >
    <div class="navbar-top">
      <div class="nav-left">
        <h4 class="brand" @click="jumpToHomePage()">Renting Buddy</h4>
      </div>
      <div class="nav-right" v-if="this.signedIn==false">
        <button type="button" v-if="signIn" @click="jmpToLoginPage()" class="custom-btn button-outline">Sign in</button>
        <button type="button"  v-if="signUp" @click="jmpToRegistrationPage()"  style="margin-right: 0" class="custom-btn button-outline">Register</button>
      </div>
      <div class="nav-right" v-else>
        <img v-bind:src="'data:image/jpeg;base64,' + loggedPicture" style="width: 50px; height: 40px;">
        {{loggedUser.username}}
        <button type="button"  @click="logout()" class="custom-btn button-outline" >Logout</button>
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
     if(localStorage.getItem("jwt")!="null"){
        // this.signIn=true;
        // this.signUp=true;
        // this.signedIn=false;
        this.signIn=false;
        this.signUp=false;
        this.signedIn=true;      
        axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {this.loggedUser=response.data});
        axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.loggedPicture=response.data));
      }else{
        this.signIn=true;
        this.signUp=true;
        this.signedIn=false;
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
  background: #00587a;
  min-height: 54px;
}

.brand {
  color: #ffffff;
  margin-top: auto;
  margin-bottom: 0;
  font-weight: bold;
}

.navbar-top {
  position: relative;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  margin: auto;
  width: 60%;
  padding-bottom: 0;
  min-width: 500px;
  }

.navbar-top button {
  margin-inline: 10px;
  }

.brand:hover {
  cursor: pointer;
}

</style>
