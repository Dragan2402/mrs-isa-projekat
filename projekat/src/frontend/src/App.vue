<template>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <nav class="navbar" >     
      <img class="brand" @click="jumpToHomePage()" src="@/assets/app-logo.png">
      <ul>
        <li>
          <a href="#">Sing In</a>
        </li>
        <li>
          <a href="#">Register</a>
        </li>        
      </ul>
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
  
.navbar {
    background-color: #333333;
    display: flex;
    
    align-items: center;
  }
  
 
.navbar a {
    color: #e7e7e7;
  }
  
.navbar ul {
   /* Let's reset the margin and padding of the list so it's is flush. */
    margin: 0;
    padding: 0;
    /* Let's remove the bullet points */
    list-style-type: none;
    /* display the list flex and align the items centered */
    display: flex;
    align-items:center;
  }
  
.navbar .brand {
    /*Let's make the anchor display block.
       This will ensure when we get the full width of the container.
    */
    
   /* Remove padding from top/bottom and add 24px padding right/left */
    padding: 0 24px;
    min-width: 60px;
    max-height: 70px;
  }
  
.navbar ul li a {
    color: #e7e7e7;
    padding: 24px;
    /*Let's make the anchor display block.
       This will ensure when we hover, focus, or is active we get the full width and height 
        of the container.
    */
    display: block;
  }
  
  /* When a user hovers, focuses or if the link is active change the background */
.navbar ul a:hover,
.navbar ul a:focus,
.navbar ul .active {
    background-color: #272727;
  }
</style>
