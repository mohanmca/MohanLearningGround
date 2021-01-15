## Install React Router DOM 

*  ```bash
          npx create-react-app routing
          npm install react-router-dom
   ```

## Create Router

```javascript
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';

function App() {
     return (
          <Router>
               <div className='App'>               
                    <Switch>
                         <Route path='/home' component={Home}/>
                         <Route path='/about' component={About}/>
                         <Route path='/shop' component={Shop}/>
                    </Switch>
               </div>
          </Router>
     )
}

import {Link} from 'react-router-dom';

const Nav = function() {
     return (
          <Link style={navStyle} to='/about'>
               <ul></ul>
               <ul></ul>
          </Link>
     )

} 

```
