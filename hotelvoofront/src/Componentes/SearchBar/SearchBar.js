import React from 'react';
import 'antd/dist/antd.css';
import { Row, Col, Input, AutoComplete } from 'antd';
import './SearchBar.css'
import axios from 'axios';

class SearchBar extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      cities: [], citiesName: []
    }
    this.handleSelectSearch = this.handleSelectSearch.bind(this);
  }

  componentDidMount() {
    axios.get('http://localhost:8080/city').then(res => {
      const citiesRes = res.data;
      this.setState({ cities: citiesRes });

      var citiesName = citiesRes.map(value => {
        var cityName = { value: value.name };
        return cityName;
      });
      this.setState({ citiesName: citiesName });

    }, error => {
      console.log(error);
    });
  }

  handleSelectSearch(value) {
    const cities = this.state.cities;
    var result = cities.filter(city => {
      return value.localeCompare(city.name) === 0;
    });

    this.props.onCitySelect(result[0].id);
  }

  render() {
    const citiesName = this.state.citiesName;
    return (
      <Row className={"searchBarRow"}>
        <Col span={8} offset={8}>
          <AutoComplete className={"searchBarAutoComplete"} dropdownMatchSelectWidth={250} options={citiesName}
            onSelect={this.handleSelectSearch}
            filterOption={(inputValue, option) => option.value.toUpperCase().indexOf(inputValue.toUpperCase()) !== -1} >

            <Input.Search
              placeholder="Para Onde Deseja Ir ?"
              size="large"
              className={'searchInput'}
            />

          </AutoComplete>
        </Col>
      </Row>
    );
  }
}

export default SearchBar;