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
      <div class="nav-right" v-else>
        <img v-bind:src="'data:image/jpeg;base64,' + loggedPicture" style="width: 50px; height: 40px;" @click="jmpToUserProfile()">
        <!--        <router-link v-if="loggedUser.accountType==='CLIENT'" class="link-light text-decoration-none" :to="{name: 'clientProfile'}">{{loggedUser.username}}</router-link>-->
        <label class="link-light text-decoration-none" @click="jmpToUserProfile()">{{loggedUser.username}}</label>
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

</style>
