<template>
  <h1>Welcome to App</h1>
  <p>{{msg}}</p>
  <router-view></router-view>
</template>

<script>
export default {
  data() {
    return {
      msg: ''
    }
  },
  mounted() {
    fetch("/api/clients/all")
        .then((response) => response.text())
        .then((data) => {
         this.msg = data;
        });
  }
}
</script>
