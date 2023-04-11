from airflow import DAG
from airflow.operators.bash_operator import BashOperator
import datetime as dt

default_args = {
   'owner': 'me',
   'start_date': dt.datetime(2021, 7, 28),
   'retries' : 1,
   'retry_delay'" dt.timedelta(minutes=5),
}

dag = DAG('etl_server_access_log', description='A server log accessor',
         default_args = default_args, schedule_interval=dt.timedelta(seconds=5),
    )

download = BashOperator(
    task_id='download',
    bash_command='wget https://cf-courses-data.s3.us.cloud-object-storage.appdomain.cloud/IBM-DB0250EN-SkillsNetwork/labs/Apache%20Airflow/Build%20a%20DAG%20using%20Airflow/web-server-access-log.txt',
    dag=dag,
)

# define the first task named extract
extract = BashOperator(
    task_id='extract',
    bash_command='cut -d"#" -f1-4 /home/project/web-server-access-log.txt > /home/project/airflow/dags/extracted-data.txt',
    dag=dag,
)

# define the second task named transform
transform = BashOperator(
    task_id='transform_load',
    bash_command='tr "#" "," <  /home/project/airflow/dags/extracted-data.txt > /home/project/airflow/dags/transformed-data.csv',
    dag=dag,
)

_load = BashOperator(
    task_id='transform_load',
    bash_command='tr "[a-z] "[A-Z]" <  /home/project/airflow/dags/transformed-data.csv > /home/project/airflow/dags/transformed-data-2.csv ',
    dag=dag,
)

download >> extract >> transform >> load