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
          <button type="button" class="btn btn-warning">Sign in</button>
        </li>
        <li>
          <button type="button" class="btn btn-warning">Register</button>
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

nav {
  background: darkgrey;
}

.navbar {
  margin:auto;
  width: 65%;
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
  margin-left: 15px;
  }

.navbar button {
  padding: 5px 25px;
  margin: 10px;
  }

.navbar .brand:hover {
  cursor: pointer;
  -webkit-filter: brightness(80%);
}


</style>
