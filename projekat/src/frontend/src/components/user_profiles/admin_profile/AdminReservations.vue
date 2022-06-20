<template>
  <div style="border: 1px solid black; margin: 10px; padding: 10px"
       v-for="(reservation, index) in this.reservations"
       v-bind:index="index" :key="reservation.id">
    <div>Name: {{ reservation.rentingEntityName }}</div>
    <div>Price: {{ reservation.price }}</div>
    <div>Fee: {{ reservation.fee }}</div>
    <div>Date: {{ reservation.start }}</div>
    <div>Duration: {{ reservation.duration }}</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminReservations",
  data() {
    return {
      reservations: []
    }
  },
  mounted() {
    axios.get("api/admins/reservations/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reservations = response.data))
  },
  methods: {

  }
}
</script>

<style scoped>

</style>