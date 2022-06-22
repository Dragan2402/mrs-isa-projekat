<template>
  <div style="display: flex; justify-content: center">
  <div class="main-wrapper">
    <div class="for-container" v-for="(reservation, index) in this.reservations" v-bind:index="index" :key="reservation.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Entity:</b> {{ reservation.rentingEntityName }}</div>
          <div><b>Profit:</b> {{ (reservation.price * reservation.fee).toFixed(2) }} &#8364; </div>
          <div><b>Date:</b> {{ reservation.start }}</div>
        </div>
      </div>
    </div>
  </div>
    <div class="inner-container" style="text-align: left; display: block; margin-top: 15px; margin-left: 15px; width: 200px; height: 200px">
      <div><b>Total profit:</b> {{ totalProfit.toFixed(2) }} &#8364;</div>
      <br>
      <div><b>Reservations count:</b> {{ reservations.length }}</div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminReservations",
  data() {
    return {
      reservations: [],
      totalProfit: 0,
    }
  },
  mounted() {
    axios.get("api/admins/reservations/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
      this.reservations = response.data;
      for(const res of this.reservations) {
        this.totalProfit += res.price * res.fee;
      }
    })
  },
  methods: {

  }
}
</script>

<style scoped>

.main-wrapper {
  margin-bottom: 20%;
}

.for-container {
  display: flex;
  justify-content: center;
  margin-top: 15px
}

.inner-container {
  border: 1px solid #00587a;
  display: flex;
  padding: 10px;
  border-radius: 5px
}

.left-container {
  text-align: left;
  width: 30rem;
}

</style>