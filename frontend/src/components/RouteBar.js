import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import style from './RouteBar.module.css';

class RouteBar extends React.Component {
    renderParents() {
        const propName = this.props.language === 'ar' ? 'arName' : 'enName';
        let lastSegment;
        if (!this.props.extraSegment) {
            lastSegment = <React.Fragment>
                <div key={`pre_${this.props.topic.id}`} className={`ui label grey ${style.routeItem}`}>/</div>
                <div key={this.props.topic.id} className={`ui label yellow ${style.routeItem}`}>
                    {`${this.props.topic[propName]}`}
                </div>
            </React.Fragment>;
        } else {
            lastSegment = <React.Fragment>
                <div key={`pre_${this.props.topic.id}`} className={`ui label grey ${style.routeItem}`}>/</div>
                <Link key={this.props.topic.id} to={`/topic/${this.props.topic.id}`} className={`ui label teal ${style.routeItem}`}>
                    {`${this.props.topic[propName]}`}
                </Link>
                <div key={`post_${this.props.topic.id}`} className={`ui label grey ${style.routeItem}`}>/</div>
                <div key='extraSegment' className={`ui label yellow ${style.routeItem}`}>
                    {this.props.extraSegment}
                </div>
            </React.Fragment>;
        }

        return <React.Fragment>
            {
                this.props.topic.parents.map(parent => {
                    return <React.Fragment key={parent.id}>
                        <div key={`pre_${parent.id}`} className={`ui label grey ${style.routeItem}`}>/</div>
                        <Link key={parent.id} to={`/topic/${parent.id}`} className={`ui label teal ${style.routeItem}`}>
                            {parent[propName]}
                        </Link>
                    </React.Fragment>
                })
            }
            {lastSegment}
        </React.Fragment>
    }

    render() {
        return (
            <div className="ui card">
                <div className="content">
                    <div className="description">
                        {this.renderParents()}
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        topic: state.topic[ownProps.topicId],
        language: state.language
    };
};

export default connect(
    mapStateToProps,
    {}
)(RouteBar);
