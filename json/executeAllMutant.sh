for i in $(ls mutants | sort -n); do make m=$i >> mutantEvalRecord/$i.rpt; done
