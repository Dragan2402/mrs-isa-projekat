<template>
  <div class="left-bar">
    <h5 style="color: white; font-weight: bold">Search</h5>
    <div class="left-bar-text">Destination name:</div>
    <div>
      <input class="input-group-text" type="text" placeholder="Filter by name, address..." v-model="filter"/>
    </div>
    <div class="left-bar-text">Date:</div>
<!--    <Datepicker v-model="availabilityInterval" :format="formatRange" range clearable/>-->
    <v-date-picker v-model="availabilityInterval" is-range>
      <template v-slot="{ inputValue, inputEvents }">
        <div class="flex justify-center items-center">
          <input
              :value="inputValue.start"
              v-on="inputEvents.start"
              class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300 mb-1 w-100"
          />
          <input
              :value="inputValue.end"
              v-on="inputEvents.end"
              class="border px-2 py-1 w-32 rounded focus:outline-none focus:border-indigo-300 w-100"
          />
        </div>
      </template>
    </v-date-picker>
    <div class="left-bar-text">Sort by:</div>
    <div class="sort">
      <select class="form-select" v-model="sortType" name="example">
        <option value=0>Name descending</option>
        <option value=1>Name ascending</option>
        <option value=2>Price descending</option>
        <option value=3>Price ascending</option>
      </select>
    </div>
  </div>
  <div class="home-entities-div">
    <div class="home-list-entities" v-for="vacationHouse in filteredVacationHouses" :key="vacationHouse.id">
      <div class="home-entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + vacationHouse.img" v-bind:alt="vacationHouse.id" style="width: 200px; height: 150px; cursor: pointer"></div>
      <div class="home-entity-content"><h4 style="font-weight: bold; cursor: pointer">{{ vacationHouse.name }}</h4>
<!--        <div class="home-entity-description"><i class="bi bi-geo-alt-fill"> {{ vacationHouse.address }} </i></div>-->
<!--        <div class="home-entity-description">{{ vacationHouse.promoDescription }}</div>-->
<!--        <vue3-star-ratings class="star-ratings" v-model="vacationHouse.rating" starSize="15" :showControl=false :disableClick=true :step=0 />-->
<!--        <span style="color: #585858;">({{vacationHouse.reviewsNumber}})</span>-->
      </div>
      <div class="home-entity-price">
        <div><h5 style="font-weight: bold">{{ vacationHouse.priceList }}&euro;</h5></div>
        <div>
          <button class="custom-btn button-primary">Explore</button>
          <button type="button" class="custom-btn button-primary" @mousedown="vacationHouseHasReservations(vacationHouse.id)" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>
        </div>
      </div>

      <div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-sm">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title mx-auto" v-if="hasReservations">This vacation house is currently reserved</h5>
              <h5 class="modal-title mx-auto" v-if="!hasReservations">Are you sure?</h5>
            </div>
            <div class="modal-footer">
              <button type="button" class="custom-btn button-close" data-bs-dismiss="modal">Cancel</button>
              <button v-if="!hasReservations" type="button" class="custom-btn button-primary" @click="deleteVacationHouse(vacationHouse.id)" data-bs-dismiss="modal">Delete</button>
<!--              @click="deleteVacationHouse(vacationHouse.id)"-->
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>


</template>

<script>
import {computed, ref, onMounted} from "vue";
import axios from "axios";

export default {
  name: "VacationHouseOwnerHome",
  setup() {
    let vacationHouses  = ref([{}]);
    let filter = ref("");
    let availabilityInterval = ref(null);
    let sortType = ref(0);
    let hasReservations = ref();

    onMounted(() => {
      loadData();
    });

    function dateFromLocal(dateString) {
      if(dateString == null){
        return new Date();
      }

      const parts = dateString.split(" ");
      if(parts.length !== 2) {
        return new Date();
      }

      const dateParts = parts[0].split(".");
      if( dateParts.length !== 3){
        return new Date();
      }

      const hourParts = parts[1].split(":");
      if( hourParts.length !== 2){
        return new Date();
      }

      const day = dateParts[0];
      const month = dateParts[1];
      const year = dateParts[2];
      const hours = hourParts[0];
      const minutes = hourParts[1];
      // new Date(year, month, day, hours, minutes, seconds, milliseconds)
      // const date = new Date(year,month-1,day,hours,minutes);
      return new Date(year,month-1,day,hours,minutes);
    }

    function includes(row) {
      if(row.name === undefined || row.address === undefined || row.priceList === undefined || filter.value === undefined)
        return false;

      const name = row.name.toString().toLowerCase();
      const address = row.address.toString().toLowerCase();
      const price = row.priceList.toString().toLowerCase();
      const searchTerm = filter.value.toLowerCase();

      const availableFrom = dateFromLocal(row.availableFrom);
      const availableTo= dateFromLocal(row.availableTo);
      let dateInInterval = false;
      let dateFilterOn = false;

      if(availabilityInterval.value != null){
        dateFilterOn = true;
        let fromFilter = availabilityInterval.value.start
        let toFilter = availabilityInterval.value.end
        if(fromFilter == null){
          fromFilter = new Date();
        }
        if(toFilter == null){
          toFilter = new Date();
        }
        dateInInterval = availableFrom <= fromFilter && availableTo >= toFilter;
      }

      if(dateFilterOn && dateInInterval) {
        return (name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm))
      }
      else if(dateFilterOn && !dateInInterval) {
        return false;
      }
      return (name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm))
    }

    function sort(a, b) {
      if(sortType.value === 0) {
        let fa = a.name.toLowerCase(), fb = b.name.toLowerCase();
        if (fa < fb) {
          return -1
        }
        if (fa > fb) {
          return 1
        }
        return 0
      }
      else if(sortType.value === 1) {
        let fa = a.name.toLowerCase(), fb = b.name.toLowerCase();
        if (fa > fb) {
          return -1
        }
        if (fa < fb) {
          return 1
        }
        return 0
      }
      if(sortType.value === 2) {
        let fa = parseInt(a.priceList), fb = parseInt(b.priceList)
        if (fa > fb) {
          return -1
        }
        if (fa < fb) {
          return 1
        }
        return 0
      }
      else if (sortType.value === 3) {
        let fa = parseInt(a.priceList), fb = parseInt(b.priceList);
        if (fa < fb) {
          return -1
        }
        if (fa > fb) {
          return 1
        }
        return 0
      }
    }

    const filteredVacationHouses = computed(() => {
      return vacationHouses.value
          .filter(row => {
            return includes(row);
          })
          .sort((a, b) => {
            return sort(a, b);
          })
    })

    function loadData() {
      axios
          .get(`api/vacationHouses/loggedVacationHouseOwner/all`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            vacationHouses.value = response.data;
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    function vacationHouseHasReservations(id) {
      axios
          .get(`api/vacationHouses/loggedVacationHouseOwner/${id}/hasReservations`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            hasReservations.value = response.data;
          });
    }

    function deleteVacationHouse(id) {
      axios
          .delete(`api/vacationHouses/loggedVacationHouseOwner/${id}`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            console.log(response.data);
            loadData();
          });
    }

    return {
      filter,
      sortType,
      availabilityInterval,
      filteredVacationHouses,
      hasReservations,
      vacationHouseHasReservations,
      deleteVacationHouse
    }
  }
}
</script>

<style scoped>

</style>