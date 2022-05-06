<template>
  <nav class="navbar" >
    <div class="navbar-top">
      <div class="nav-left">
        <img class="brand" @click="jumpToHomePage()" src="@/assets/app-logo.png">
      </div>
      <div class="nav-right">
        <button type="button" v-if="signIn" class="btn btn-warning" >Sign in</button>
        <button type="button"  v-if="signUp" @click="jmpToRegistrationPage()"  class="btn btn-warning">Register</button>
      </div>
    </div>
  </nav>
  <router-view></router-view>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      signIn: true,
      signUp: true,
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
