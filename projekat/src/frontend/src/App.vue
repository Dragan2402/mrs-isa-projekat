<template>
  <nav class="navbar" >
    <div class="navbar-top">
      <div class="nav-left">
        <h4 class="brand" @click="jumpToHomePage()">Renting Buddy</h4>
      </div>
      <div class="nav-right">
        <button type="button" v-if="signIn" class="custom-btn button-outline">Sign in</button>
        <button type="button"  v-if="signUp" @click="jmpToRegistrationPage()"  style="margin-right: 0" class="custom-btn button-outline">Register</button>
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
  background: #00587a;
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
  }

.navbar-top button {
  margin-inline: 10px;
  }

.brand:hover {
  cursor: pointer;
}

</style>
