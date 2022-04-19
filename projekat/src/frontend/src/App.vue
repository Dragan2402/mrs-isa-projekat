<template>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <nav>
  <div class="navbar">
    <div class="nav-left">
      <img class="brand" @click="jumpToHomePage()" src="@/assets/app-logo.png">
    </div>
    <div class="nav-right">
      <ul>
        <li>
          <a href="#">Sing In</a>
        </li>
        <li>
          <a href="#">Register</a>
        </li>        
      </ul>
    </div>
    </div>
  </nav>
  <router-view></router-view>
</template>

<script>
export default {
  name: "App",
  methods:{
    jumpToHomePage(){
      this.$router.push("/");
    }
  }
  
}
</script>

<style>
a {
    text-decoration: none;
  }

nav {
  background: #333333;
}

.navbar {
  margin:auto;
  width: 70%;
  }

.navbar a {
    color: #e7e7e7;
  }

.navbar ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
    display: flex;
    align-items:center;
  }

.navbar .brand {
  width: 70px;
  margin-left: 24px;
  }

.navbar ul li a {
    color: #e7e7e7;
    padding: 0 24px;
    display: block;
  }

.navbar ul a:hover,
.navbar ul a:focus,
.navbar ul .active {
    background-color: #272727;
  }

.navbar .brand:hover {
  cursor: pointer;
}


</style>
