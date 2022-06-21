<template>
  <div class="left-bar">
    <h5 class="left-bar-heading">Reservations</h5>
  </div>

  <div class="home-entities-div">
    <div class="home-list-entities" v-for="reservation in reservations" :key="reservation.id">
      <div class="home-entity-picture">
        <img v-bind:src="'data:image/jpeg;base64,' + reservation.img" v-bind:alt="reservation.id" style="width: 200px; height: 150px; cursor: pointer">
      </div>
      <div class="home-entity-content">
        <h4 style="font-weight: bold; cursor: pointer">{{ reservation.rentingEntityName }}</h4>
        <div class="home-entity-description">
          <p>Od: {{ reservation.start }}</p>
          <p>Do: {{ reservation.end }}</p>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

export default {
  name: "OwnerReservations",
  setup() {
    const router = useRouter();

    let reservations = ref([{}]);
    let entityTypeInfo = ref({
      urlPart: ""
    });

    onMounted(() => {
      axios
          .get("/api/users/loggedUser",
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            if(response.data.accountType === "VH_OWNER") {
              entityTypeInfo.value = {
                urlPart: "vacationHouseOwners/loggedVacationHouseOwner",
              };
            } else if (response.data.accountType === "SH_OWNER") {
              entityTypeInfo.value = {
                urlPart: "shipOwner/loggedShipOwner"
              };
            } else {
              router.push({name: "homePage"});
            }
            loadData();
          })
          .catch(error => {
            console.log(`OwnerHome catch users/loggedUser: ${error.response}`);
          });
    });

    function loadData() {
      axios
          .get(`api/${entityTypeInfo.value.urlPart}/reservations`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            reservations.value = response.data;
            console.log(reservations.value);
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    return {
      reservations
    }
  }
}
</script>

<style scoped>

</style>