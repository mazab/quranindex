import React from 'react';
import { Field, reduxForm } from 'redux-form';

class SearchBox extends React.Component {
  renderInput = ({ input }) => {
    return (
      <React.Fragment>
        <i className="search icon link" onClick={this.props.handleSubmit(this.onSubmit)} />
        <input {...input} autoComplete="off" onKeyUp={this.props.handleSubmit(this.onSubmit)} />
      </React.Fragment>
    );
  };

  onSubmit = formValues => {
    this.props.onSubmit(formValues);
  };

  render() {
    return (
      <Field name="search" component={this.renderInput} />
    );
  }
}

export default reduxForm({
  form: 'streamForm',
})(SearchBox);