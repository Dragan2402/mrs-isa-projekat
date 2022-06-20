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
    <div class="home-list-entities" v-for="entity in filteredEntities" :key="entity.id">
      <div class="home-entity-picture"><img v-bind:src="'data:image/jpeg;base64,' + entity.img" v-bind:alt="entity.id" style="width: 200px; height: 150px; cursor: pointer"></div>
      <div class="home-entity-content">
        <h4 style="font-weight: bold; cursor: pointer">{{ entity.name }}</h4>
        <div class="home-entity-description">
          <i class="bi bi-geo-alt-fill"></i> {{ entity.address }}
        </div>
        <vue3-star-ratings class="star-ratings" v-model="entity.rating" starSize="15" :showControl=false :disableClick=true :step=0 />
        <span style="color: #585858;">({{entity.reviewsNumber}})</span>
      </div>
      <div class="home-entity-price h-100 d-flex">
        <div class="align-self-center">
          <button type="button" class="custom-btn button-primary d-block w-100" @click="openEntityProfile(entity.id)">Open Profile</button>
          <button type="button" class="custom-btn button-primary d-block w-100" @click="entityHasReservations(entity.id)" data-bs-toggle="modal" data-bs-target="#updateModal">Update</button>
          <button type="button" class="custom-btn button-primary d-block w-100" @click="entityHasReservations(entity.id)" data-bs-toggle="modal" data-bs-target="#deleteModal">Delete</button>
        </div>
      </div>

      <div class="modal fade" id="updateModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
          <div class="modal-content">
            <div class="modal-header" v-if="hasReservations">
              <h5 class="modal-title mx-auto">This {{entityTypeInfo.name}} is currently reserved</h5>
            </div>
            <div class="modal-body" v-if="!hasReservations">
              <div class="form-floating mb-3">
                <input v-model="entity.name" type="text" class="form-control" id="floatName" placeholder="Title">
                <label for="floatName">Title</label>
              </div>
              <div class="form-floating mb-3">
                <input v-model="entity.address" type="text" class="form-control" id="floatAddress" placeholder="Address">
                <label for="floatAddress">Address</label>
              </div>
              <div class="form-floating mb-3">
                <textarea style="height: 100px" v-model="entity.promoDescription" type="text" class="form-control" id="floatPromoDescription" placeholder="Description"></textarea>
                <label for="floatPromoDescription">Description</label>
              </div>
              <div class="form-floating mb-3">
                <textarea style="height: 100px" v-model="entity.behaviourRules" type="text" class="form-control" id="floatBehaviourRules" placeholder="Behaviour rules"></textarea>
                <label for="floatBehaviourRules">Behaviour rules</label>
              </div>
              <div class="form-floating mb-3">
                <input v-model="entity.priceList" type="text" class="form-control" id="floatPriceList" placeholder="Price">
                <label for="floatPriceList">Price</label>
              </div>
              <div class="form-floating mb-3">
                <textarea style="height: 100px" v-model="entity.additionalInfo" type="text" class="form-control" id="floatAdditionalInfo" placeholder="Additional info"></textarea>
                <label for="floatAdditionalInfo">Additional info</label>
              </div>
              <div class="form-floating mb-3">
                <input v-model="entity.cancellationConditions" type="text" class="form-control" id="floatCancellationConditions" placeholder="Cancellation conditions">
                <label for="floatCancellationConditions">Cancellation conditions</label>
              </div>


              <!-- Vacation House specific attributes -->
              <div v-if="entityTypeInfo.name==='vacation house'" class="form-floating mb-3">
                <input v-model="entity.roomsQuantity" type="text" class="form-control" id="floatRoomsQuantity" placeholder="Rooms quantity">
                <label for="floatRoomsQuantity">Rooms quantity</label>
              </div>
              <div v-if="entityTypeInfo.name==='vacation house'" class="form-floating mb-3">
                <input v-model="entity.bedsPerRoom" type="text" class="form-control" id="floatBedsPerRoom" placeholder="Beds per room">
                <label for="floatBedsPerRoom">Beds per room</label>
              </div>



              <!-- Ship specific attributes -->
              <div v-if="entityTypeInfo.name==='ship'" class="form-floating mb-3">
                <input v-model="entity.type" type="text" class="form-control" id="floatType" placeholder="Type">
                <label for="floatType">Type</label>
              </div>
              <div v-if="entityTypeInfo.name==='ship'" class="form-floating mb-3">
                <input v-model="entity.length" type="text" class="form-control" id="floatLength" placeholder="Length">
                <label for="floatLength">Length</label>
              </div>
              <div v-if="entityTypeInfo.name==='ship'" class="form-floating mb-3">
                <input v-model="entity.engineNumber" type="text" class="form-control" id="floatEngineNumber" placeholder="Engine number">
                <label for="floatEngineNumber">Engine number</label>
              </div>
              <div v-if="entityTypeInfo.name==='ship'" class="form-floating mb-3">
                <input v-model="entity.enginePower" type="text" class="form-control" id="floatEnginePower" placeholder="Engine power">
                <label for="floatEnginePower">Engine power</label>
              </div>
              <div v-if="entityTypeInfo.name==='ship'" class="form-floating mb-3">
                <input v-model="entity.topSpeed" type="text" class="form-control" id="floatTopSpeed" placeholder="Top speed">
                <label for="floatTopSpeed">Top speed</label>
              </div>
              <div v-if="entityTypeInfo.name==='ship'" class="form-floating mb-3">
                <input v-model="entity.clientLimit" type="text" class="form-control" id="floatClientLimit" placeholder="Client limit">
                <label for="floatClientLimit">Client limit</label>
              </div>



              <!-- Fishing Class specific attributes -->



              <v-date-picker v-model="entity.availabilityInterval" :min-date="new Date()" :from-date="entity.fromDate" mode="dateTime" is-range is-expanded is24hr ></v-date-picker>
            </div>
            <div class="modal-footer">
              <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              <button v-if="!hasReservations" type="button" @click="updateEntity(entity)" class="custom-btn button-primary" data-bs-dismiss="modal">Update</button>
            </div>
          </div>
        </div>
      </div>

      <div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title mx-auto" v-if="hasReservations">This {{entityTypeInfo.name}} is currently reserved</h5>
              <h5 class="modal-title mx-auto" v-if="!hasReservations">Are you sure?</h5>
            </div>
            <div class="modal-footer">
              <button type="button" class="custom-btn button-close" data-bs-dismiss="modal">Cancel</button>
              <button v-if="!hasReservations" type="button" class="custom-btn button-primary" @click="deleteEntity(entity.id)" data-bs-dismiss="modal">Delete</button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>


</template>

<script>
import {computed, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

export default {
  name: "OwnerHome",
  setup() {
    const router = useRouter();

    let entityTypeInfo = ref({
      name: "",
      urlPart: "",
      routeName: ""
    });

    let entities  = ref([{}]);
    let filter = ref("");
    let availabilityInterval = ref(null);
    let sortType = ref(0);
    let hasReservations = ref();

    let range = ref({
      start: new Date(),
      end: new Date()
    });
    // let bars = ref([]);
    // let takenDatesPoppers = ref([]);
    // let minDate = ref(null);
    // let maxDate = ref(null);

    const filteredEntities = computed(() => {
      return entities.value
          .filter(row => {
            return includes(row);
          })
          .sort((a, b) => {
            return sort(a, b);
          })
    })

    // const attributes = computed(() => {
    //   return [
    //     ...bars.value,
    //     // Attributes for todos
    //     ...takenDatesPoppers.value.map(todo => ({
    //       dates: todo.dates,
    //       dot: {
    //         color: todo.color,
    //         class: todo.isComplete ? 'opacity-75' : '',
    //       },
    //       popover: {
    //         label: todo.description,
    //       },
    //       customData: todo,
    //     })),
    //   ];
    // })

    onMounted(() => {
      axios
          .get("/api/users/loggedUser",
          { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            if(response.data.accountType === "VH_OWNER") {
              entityTypeInfo.value = {
                name: "vacation house",
                urlPart: "vacationHouses/loggedVacationHouseOwner",
                routeName: "vacationHouseProfile"
              };

            } else if (response.data.accountType === "SH_OWNER") {
              entityTypeInfo.value = {
                name: "ship",
                urlPart: "ships/loggedShipOwner",
                routeName: "shipProfile"
              };

            // else if INSTRUCTOR, ADMIN

            } else {
              router.push({name: "homePage"});
            }

            loadData();
          })
          .catch(error => {
            console.log(`OwnerHome catch users/loggedUser: ${error.response}`);
            // root.accessToken = null;
            // localStorage.setItem("jwt", null);
            // router.push("/loginPage");
          });
    });

    function loadData() {
      console.log("loadData:")
      console.log(entityTypeInfo.value);
      axios
          .get(`api/${entityTypeInfo.value.urlPart}/all`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            entities.value = response.data;
            entities.value.forEach(entity => {
              entity.availabilityInterval = {
                start: null,
                end: null
              }
              entity.availabilityInterval.start = stringToDateTime(entity.availableFrom);
              entity.availabilityInterval.end = stringToDateTime(entity.availableTo);
              entity.fromDate = entity.availabilityInterval.start;
            })
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    function entityHasReservations(id) {
      axios
          .get(`api/${entityTypeInfo.value.urlPart}/${id}/hasReservations`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            hasReservations.value = response.data;
          });
    }

    function updateEntity(entity) {
      entity.availableFrom = dateTimeToString(entity.availabilityInterval.start);
      entity.availableTo = dateTimeToString(entity.availabilityInterval.end);

      axios
          .put(`api/${entityTypeInfo.value.urlPart}/${entity.id}`, entity,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            console.log(response.data);
            loadData();
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    function deleteEntity(entityId) {
      axios
          .delete(`/api/${entityTypeInfo.value.urlPart}/${entityId}`,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            console.log(response.data);
            loadData();
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    function openEntityProfile(entityId) {
      router.push({
        name: entityTypeInfo.value.routeName,
        params: {
          id: entityId
        }
      });
    }

    function stringToDateTime(s) {
      let tokens = s.split(" ");
      let date = tokens[0];
      let time = tokens[1];

      tokens = date.split(".");
      let day = parseInt(tokens[0]);
      let month = parseInt(tokens[1]) - 1;
      let year = parseInt(tokens[2]);

      tokens = time.split(":");
      let hour = parseInt(tokens[0]);
      let minute = parseInt(tokens[1]);

      return new Date(year, month, day, hour, minute);
    }

    function dateTimeToString(dt) {
      let day = ("0" + dt.getDate()).slice(-2);
      let month = ("0" + (dt.getMonth() + 1)).slice(-2);
      let year = dt.getFullYear();
      let hour = ("0" + dt.getHours()).slice(-2);
      let minute = ("0" + dt.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hour}:${minute}`;
    }

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

    return {
      entityTypeInfo,
      filter,
      sortType,
      availabilityInterval,
      filteredEntities,
      hasReservations,
      range,
      // attributes,
      // minDate,
      // maxDate,
      entityHasReservations,
      updateEntity,
      deleteEntity,
      openEntityProfile
    }
  }
}
</script>

<style scoped>

</style>