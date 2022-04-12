<template>
  <h1>TEST</h1>
  <router-link to="/helloWorld">Link</router-link>
  <button @click="goToUserProfile()">CheckProfile</button>
</template>

<script>
export default {
  name: "TestPage",
  data() {
    return {

    }
  },
  //this.$router.push({name:'home', params: {id: '[paramdata]'}});
  methods: {
    goToUserProfile(){
    this.$router.push('/clientProfile'); 
      }
  }
  
}
</script>

<style scoped>

</style>