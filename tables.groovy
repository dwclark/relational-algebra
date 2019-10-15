import static Constants.*

originalColumns = ['sid', 'sname', 'gradYear', 'majorId' ]

listStudents =
    [[ 1, 'joe', 2004, 10 ],
     [ 2, 'amy', 2004, 20 ],
     [ 3, 'max', 2005, 10 ],
     [ 4, 'sue', 2005, 20 ],
     [ 5, 'bob', 2003, 30 ],
     [ 6, 'kim', 2001, 20 ],
     [ 7, 'art', 2004, 30 ],
     [ 8, 'pat', 2001, 20 ],
     [ 9, 'lee', 2004, 10 ] ]

dupListStudents =
    [[ 1, 'joe', 2009, 10 ],
     [ 2, 'amy', 2004, 20 ],
     [ 3, 'max', 2005, 10 ],
     [ 4, 'sue', 2005, 20 ],
     [ 5, 'amy', 2003, 30 ],
     [ 6, 'kim', 2001, 20 ],
     [ 7, 'joe', 2004, 30 ],
     [ 8, 'pat', 2001, 20 ],
     [ 9, 'bob', 2004, 10 ] ]

students = new RowTable(originalColumns, listStudents)

dupStudents = new RowTable(originalColumns, dupListStudents)

select = new Select(students, { majorId == 10 })

project = new Project(students, { sname; gradYear })

selectProject = new Project(select, { sname; gradYear })

renamed = new Rename(students, { sid; studentId; sname; studentName; })

sorted = new Sort(dupStudents, { sname; gradYear desc; })
