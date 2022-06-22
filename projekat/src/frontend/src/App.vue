<template>
  <nav class="navbar" >
    <div class="navbar-top" style="min-width: 1000px">
      <div class="nav-left">
        <h4 class="brand pointer" @click="jumpToHomePage()">Renting Buddy</h4>
      </div>
      <div class="nav-right" v-if="this.signedIn==false">
        <button type="button" v-if="signIn" @click="jmpToLoginPage()" class="custom-btn button-primary">Sign in</button>
        <button type="button"  v-if="signUp" @click="jmpToRegistrationPage()"  style="margin-right: 0" class="custom-btn button-primary">Register</button>
      </div>

      <div class="nav-right" v-else>
        <img class="pointer" v-bind:src="'data:image/jpeg;base64,' + loggedPicture" style="width: 50px; height: 40px;" @click="jmpToUserProfile()">
        <!--        <router-link v-if="loggedUser.accountType==='CLIENT'" class="link-light text-decoration-none" :to="{name: 'clientProfile'}">{{loggedUser.username}}</router-link>-->
        <label class="link-light text-decoration-none pointer" id="username-label" @click="jmpToUserProfile()">{{loggedUser.username}}</label>
        <button type="button"  @click="logout()" class="custom-btn button-outline" >Logout</button>
      </div>
        
<!--      <div class="dropdown" v-else>-->
<!--        <button class="transparent-button" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">-->
<!--        <label style="cursor: pointer" class="link-light text-decoration-none">{{loggedUser.username}}</label>-->
<!--        <img v-bind:src="'data:image/jpeg;base64,' + loggedPicture" style="width: 40px; height: 40px; border-radius: 20px; object-fit: cover; margin-left: 10px; border: 1px solid white">-->
<!--        </button>-->
<!--        <ul style="text-align: right;" class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton1">-->
<!--          <li class="dropdown-info" style="margin-top: 10px; font-weight: bold">{{loggedUser.firstName}} {{loggedUser.lastName}}</li>-->
<!--          <li class="dropdown-info">{{loggedUser.email}}</li>-->
<!--          <li class="dropdown-info">{{loggedUser.address}}</li>-->
<!--          <li class="dropdown-info">{{loggedUser.city}}, {{loggedUser.country}}</li>-->
<!--          <li class="dropdown-link" @click="jmpToUserProfile">My Profile</li>-->
<!--          <li class="dropdown-link" @click="logout">Logout</li>-->
<!--        </ul>-->
<!--      </div>-->
    </div>
    
  </nav>
  <div style="min-height:800px;">
    <router-view ></router-view>
  </div>
  <footer class="text-white text-center text-lg-start" style="background-color:#004661;maring-top:5%">
      <!-- Grid container -->
      <div class="container p-4" style="background-color:#004661;">
        <!--Grid row-->
        <div class="row mt-4">
          <!--Grid column-->
          <div class="col-lg-4 col-md-12 mb-4 mb-md-0">
            <h5 class="text-uppercase mb-4">About company</h5>

            <p>
              Our company was founded in 2010 and
              it has been the leader in the region ever since. 
              We are here to make your dreams come true and make it easier for you to choose where to spend your vacation.
            </p>

            <div class="mt-4">
              <!-- Google + -->
              <a type="button"  href="mailto:renting.buddy.co@gmail.com" class="btn btn-floating btn-light btn-lg"><i class="bi bi-google"></i></a>
              <!-- Linkedin -->
            </div>
          </div>
          <!--Grid column-->

          <!--Grid column-->
          <div class="col-lg-4 col-md-6 mb-4 mb-md-0" style="margin-top:5%;">

            <ul class="fa-ul" style="margin-left: 1.65em;">
              <li class="mb-3">
                <span class="fa-li"><i class="fas fa-home"></i></span><span class="ms-2">Novi Sad, 21000, Serbia</span>
              </li>
              <li class="mb-3">
                <span class="fa-li"><i class="fas fa-envelope"></i></span><span class="ms-2">renting.buddy.co@gmail.com</span>
              </li>
              <li class="mb-3">
                <span class="fa-li"><i class="fas fa-phone"></i></span><span class="ms-2">+ 69 00 00 000</span>
              </li>
            </ul>
          </div>
          <!--Grid column-->

          <!--Grid column-->
          <div class="col-lg-4 col-md-6 mb-4 mb-md-0">
            <h5 class="text-uppercase mb-4">Opening hours</h5>

            <table class="table text-center text-white">
              <tbody class="fw-normal">
                <tr>
                  <td>Mon - Thu:</td>
                  <td>8am - 5pm</td>
                </tr>
                <tr>
                  <td>Fri - Sat:</td>
                  <td>8am - 3pm</td>
                </tr>
                <tr>
                  <td>Sunday:</td>
                  <td>Not working</td>
                </tr>
              </tbody>
            </table>
          </div>
          <!--Grid column-->
        </div>
        <!--Grid row-->
      </div>
      <!-- Grid container -->

      <!-- Copyright -->
      <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2020 Copyright:
        <a class="text-white" href="http://www.trademarks-serbia.com/">Trademarks Serbia</a>
      </div>
      <!-- Copyright -->
    </footer>
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
        }).catch((errorP) => {
        
            if(errorP.response.status===403 || errorP.response.status===402 || errorP.response.status===401 ||errorP.response.status===401){          
              this.logout();
            }
       
      });  
        axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.loggedPicture=response.data)).catch((errorP) => {
        
            if(errorP.response.status===403 || errorP.response.status===402 || errorP.response.status===401 ||errorP.response.status===401){          
              this.logout();
            }
       
      });
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

.pointer:hover {
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
