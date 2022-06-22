<template>
  <nav class="navbar" >
    <div class="navbar-top" style="min-width: 1000px">
      <div class="nav-left">
        <h4 class="brand" @click="jumpToHomePage()">Renting Buddy</h4>
      </div>
      <div class="nav-right" v-if="this.signedIn==false">
        <button type="button" v-if="signIn" @click="jmpToLoginPage()" class="custom-btn button-primary">Sign in</button>
        <button type="button"  v-if="signUp" @click="jmpToRegistrationPage()"  style="margin-right: 0" class="custom-btn button-primary">Register</button>
      </div>
      <div class="dropdown" v-else>
        <button class="transparent-button" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
        <label style="cursor: pointer" class="link-light text-decoration-none">{{loggedUser.username}}</label>
        <img v-bind:src="'data:image/jpeg;base64,' + loggedPicture" style="width: 40px; height: 40px; border-radius: 20px; object-fit: cover; margin-left: 10px; border: 1px solid white">
        </button>
        <ul style="text-align: right;" class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton1">
          <li class="dropdown-info" style="margin-top: 10px; font-weight: bold">{{loggedUser.firstName}} {{loggedUser.lastName}}</li>
          <li class="dropdown-info">{{loggedUser.email}}</li>
          <li class="dropdown-info">{{loggedUser.address}}</li>
          <li class="dropdown-info">{{loggedUser.city}}, {{loggedUser.country}}</li>
          <li class="dropdown-link" @click="jmpToUserProfile">My Profile</li>
          <li class="dropdown-link" @click="logout">Logout</li>
        </ul>
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
      accountType: ""
    }
  }, 
  mounted(){    
     if(localStorage.getItem("jwt")!="null"){
        // this.signIn=true;
        // this.signUp=true;
        // this.signedIn=false;
     
        axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
          this.loggedUser=response.data;
          this.accountType = response.data.accountType;
          this.signIn=false;
          this.signUp=false;
          this.signedIn=true;

        });
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
    jmpToUserProfile(){
      if(this.loggedUser.accountType==="CLIENT"){
        this.$router.push({name: "clientProfile"});
      }else if (this.loggedUser.accountType==="VH_OWNER"){
        this.$router.push({name: "ownerHome"});
      }else if(this.loggedUser.accountType==="ADMIN"){
       this.$router.push({name: "adminHome"})
      }
    },
    logout(){
      this.accessToken=null;
      localStorage.setItem("jwt",null);          
      window.location.href = 'http://localhost:3000/';
     
    }
  }
  
}
</script>

<style scoped>

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

.transparent-button {
  padding: 0;
  margin: 0;
  background-color: transparent;
  background-repeat: no-repeat;
  border: none;
  cursor: pointer;
  overflow: hidden;
  outline: none;
}

.dropdown-info {
  margin: 0 20px 10px 20px;
}

.dropdown-link {
  margin: 0 20px 10px 20px;
  color: #00587a;
  cursor: pointer;
}

</style>
