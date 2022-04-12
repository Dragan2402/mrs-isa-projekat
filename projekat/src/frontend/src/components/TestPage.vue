<template>
  <h1>TEST</h1>
  <router-link to="/helloWorld">Link</router-link>
  <button @click="goToHome()">Dugme</button>
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
    goToHome(){
    this.$router.push('/helloWorld'); 
      }
  }
  
}
</script>

<style scoped>

</style>