from airflow import DAG
from airflow.operators.bash_operator import BashOperator
import datetime as dt
from airflow.utils.dates import days_ago

default_args = {
   'owner': 'Mohan',
   'start_date': days_ago(0),
   'email': ['dummy_email@gmail.com']
   'email_on_failure': True,
   'email_on_retry': True,
   'retries' : 1,
   'retry_delay': dt.timedelta(minutes=5),
}

dag = DAG('ETL_toll_data',
         schedule_interval=dt.timedelta(days=1),
         default_args = default_args,
         description='Apache Airflow Final Assignment',
)

unzip_data = BashOperator(
    task_id='download_and_unzip_data',
    bash_command='cd /home/project/airflow/dags/finalassignment; tar xvfz tolldata.tgz',
    dag=dag,
)

# Extract the columns 1:(Rowid), 2:(Timestamp), 3:(Anonymized Vehicle Number) and 4:(Vehicle type)
extract_data_from_csv = BashOperator(
    task_id='extract_data_from_csv',
    bash_command='cut -d# -f1-4 /home/project/airflow/dags/finalassignment/vehicle-data.csv > /home/project/airflow/dags/finalassignment/csv_data.csv',
    dag=dag,
)

# define the task for tsv
extract_data_from_tsv = BashOperator(
    task_id='extract_data_from_tsv',
    bash_command="cut -d$'\t' -f5-7  /home/project/airflow/dags/finalassignment/tollplaza-data.tsv > /home/project/airflow/dags/finalassignment/tsv-data.csv",
    dag=dag,
)

extract_data_from_fixed_width = BashOperator(
    task_id='extract_data_from_fixed_width',
    bash_command="cut -b1-7,59-62,63-67 < /home/project/airflow/dags/finalassignment/payment-data.txt > /home/project/airflow/dags/finalassignment/fixed_width_data.csv",
    dag=dag,
)

consolidate_data = consolidate_data(
    task_id='consolidate_data',
    bash_command='paste /home/project/airflow/dags/finalassignment/csv_data.csv /home/project/airflow/dags/finalassignment/tsv_data.csv /home/project/airflow/dags/finalassignment/fixed_width_data.csv > /home/project/airflow/dags/finalassignment/extracted_data.csv'
    dag=dag,
)

transform = BashOperator(
    task_id='transform',
    bash_command='tr "[a-z] "[A-Z]" <  /home/project/airflow/dags/extracted_data.csv" > /home/project/airflow/dags/transformed-data.csv ',
    dag=dag,
)

unzip_data >> extract_data_from_csv >> extract_data_from_tsv >> extract_data_from_fixed_width >> consolidate_data >> transform