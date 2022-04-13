<template>
  <div class="main">
    <button class="btn btn-primary" @click="goToUserProfile()">User Profile</button>
    <button class="btn btn-primary" @click="goToFishingClassProfile()">Fishing Class Profile</button>
    <button class="btn btn-primary" @click="goToVacationHousesProfile()">Vacation House Profile</button>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  methods: {
    goToUserProfile() {
      this.$router.push('/clientProfile');
    },
    goToFishingClassProfile() {
      this.$router.push('/fishingClassProfile');
    },
    goToVacationHousesProfile() {
      this.$router.push('/vacationHouses');
    }
  }
}
</script>


<style scoped>

.main {
  margin: 10px;
}

button {
  margin: 20px;
}

</style>