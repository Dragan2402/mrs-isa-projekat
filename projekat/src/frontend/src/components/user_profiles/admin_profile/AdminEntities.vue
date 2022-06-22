<template>
  <div class="main-wrapper">

    <div class="for-container" v-for="(user, index) in this.users" v-bind:index="index" :key="user.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Full Name:</b> {{ user.firstName }} {{ user.lastName }}</div>
          <div><b>Username:</b> {{ user.username }}</div>
          <div><b>Email:</b> {{ user.email }}</div>
          <div><b>Address:</b> {{ user.address }}</div>
          <div><b>City:</b> {{ user.city }}, {{ user.country }}</div>
          <div><b>Mobile number:</b> {{ user.phoneNum }}</div>
          <div><b>Penalties:</b> {{ user.penalties }}</div>
          <div v-if="user.accountType==='CLIENT'"><b>Role:</b> Client</div>
          <div v-if="user.accountType==='VH_OWNER'"><b>Role:</b> Vacation House Owner</div>
          <div v-if="user.accountType==='SH_OWNER'"><b>Role:</b> Ship Owner</div>
          <div v-if="user.accountType==='INSTRUCTOR'"><b>Role:</b> Fishing Instructor</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" @click="deleteUser(user, index)">Delete</button>
        </div>
      </div>
    </div>

    <hr>

    <div class="for-container" v-for="(vacationHouse, index) in this.vacationHouses" v-bind:index="index" :key="vacationHouse.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Name:</b> {{ vacationHouse.name }}</div>
          <div><b>Address:</b> {{ vacationHouse.address }}</div>
          <div><b>Rules:</b> {{ vacationHouse.behaviourRules }}</div>
          <div><b>Price:</b> {{ vacationHouse.priceList }}</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" @click="deleteVacationHouse(vacationHouse, index)">Delete</button>
        </div>
      </div>
    </div>

    <hr>

    <div class="for-container" v-for="(ship, index) in this.ships" v-bind:index="index" :key="ship.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Name:</b> {{ ship.name }}</div>
          <div><b>Address:</b> {{ ship.address }}</div>
          <div><b>Rules:</b> {{ ship.behaviourRules }}</div>
          <div><b>Price:</b> {{ ship.priceList }}</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" @click="deleteShip(ship, index)">Delete</button>
        </div>
      </div>
    </div>

    <hr>

    <div class="for-container" v-for="(fishingClass, index) in this.fishingClasses" v-bind:index="index" :key="fishingClass.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Name:</b> {{ fishingClass.name }}</div>
          <div><b>Address:</b> {{ fishingClass.address }}</div>
          <div><b>Rules:</b> {{ fishingClass.behaviourRules }}</div>
          <div><b>Price:</b> {{ fishingClass.priceList }}</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" @click="deleteFishingClass(fishingClass, index)">Delete</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminEntities",
  data() {
    return {
      users: [],
      vacationHouses: [],
      ships: [],
      fishingClasses: [],
    }
  },
  mounted() {
    axios.get("api/users/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.users = response.data))
    axios.get("api/vacationHouses/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.vacationHouses = response.data))
    axios.get("api/ships/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.ships = response.data))
    axios.get("api/fishingClasses/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.fishingClasses = response.data))
  },
  methods: {
    deleteUser(user, index) {
      axios.put("api/users/delete", user, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Successfully deleted!");
          this.users.splice(index,1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
    deleteVacationHouse(vacationHouse, index) {
      axios.put("api/vacationHouses/delete", vacationHouse, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Successfully deleted!");
          this.vacationHouses.splice(index,1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
    deleteShip(ship, index) {
      axios.put("api/ships/delete", ship, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Successfully deleted!");
          this.ships.splice(index,1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
    deleteFishingClass(fishingClass, index) {
      axios.put("api/fishingClasses/delete", fishingClass, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Successfully deleted!");
          this.fishingClasses.splice(index,1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    }
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
  width: 300px
}

.right-container {
  display: flex;
  margin: auto 100px auto 200px
}

</style>