import axios from 'axios'

const VACATION_HOUSE_API_BASE_URL = 'http://localhost:3000/api/'

class VacationHouseService {
    getVacationHouses() {
        return axios.get(VACATION_HOUSE_API_BASE_URL)
    }
}
